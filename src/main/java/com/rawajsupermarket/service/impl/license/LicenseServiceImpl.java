package com.rawajsupermarket.service.impl.license;

import com.rawajsupermarket.dto.license.response.LicenseStatusResponse;
import com.rawajsupermarket.entity.Store;
import com.rawajsupermarket.entity.license.LicenseState;
import com.rawajsupermarket.exception.LocalizedException;
import com.rawajsupermarket.exception.ResourceNotFoundException;
import com.rawajsupermarket.repository.StoreRepository;
import com.rawajsupermarket.repository.license.LicenseStateRepository;
import com.rawajsupermarket.service.license.LicenseService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

// Renewal codes are RS256-signed JWTs produced offline by the vendor (see
// tools/LicenseCodeGenerator.java) using a private key that never ships with
// this app - only the matching public key (license-public-key.pem, safe to
// ship) is used here, and only to verify, never to produce a valid code. This
// is the one piece that can't be simplified further: a plain stored date or a
// shared secret baked into this app would be readable/editable by the store's
// own admin on their own machine, defeating the whole point.
@Service
@RequiredArgsConstructor
@Slf4j
public class LicenseServiceImpl implements LicenseService {

    // Deliberately generous (not a few minutes): a brief forward clock glitch
    // followed by a legitimate correction back to the real time (NTP resync,
    // timezone mix-up, a user fixing a fat-fingered date) must never look like
    // tampering and lock out a paying customer - subscriptions are monthly, so
    // the realistic bypass attempt this guards against is rolling back weeks,
    // not minutes.
    private static final Duration CLOCK_ROLLBACK_TOLERANCE = Duration.ofHours(24);

    private final LicenseStateRepository repository;
    private final StoreRepository storeRepository;

    private PublicKey publicKey;

    @PostConstruct
    private void loadPublicKey() {
        try (InputStream is = getClass().getResourceAsStream("/license-public-key.pem")) {
            if (is == null) {
                throw new IllegalStateException("license-public-key.pem not found on classpath");
            }
            String pem = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(pem);
            this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        } catch (Exception e) {
            throw new IllegalStateException("Could not load license public key", e);
        }
    }

    @Override
    @Transactional
    public LicenseStatusResponse getStatus(Long storeId) {
        String licenseKey = storeRepository.findById(storeId)
                .map(Store::getLicenseKey)
                .orElse(null);

        LicenseState state = repository.findByStoreId(storeId).orElse(null);
        if (state == null) {
            // No row yet = never licensed = unlimited, not locked. Keeps this
            // feature from retroactively locking out any existing store the
            // moment it ships, until you deliberately send that store a code.
            return LicenseStatusResponse.builder().expired(false).expiresAt(null).licenseKey(licenseKey).build();
        }

        Instant now = Instant.now();

        // The system clock was rolled back behind where we've already seen it -
        // treat as locked no matter what expiresAt says, otherwise a customer
        // could "un-expire" a lapsed subscription just by changing their PC's
        // date. lastSeenAt only ever advances (never reset backward), including
        // while a rollback is in progress, so a repeated attempt is still
        // caught against the same real reference point.
        boolean clockRolledBack = state.getLastSeenAt() != null
                && now.isBefore(state.getLastSeenAt().minus(CLOCK_ROLLBACK_TOLERANCE));

        if (!clockRolledBack && (state.getLastSeenAt() == null || now.isAfter(state.getLastSeenAt()))) {
            state.setLastSeenAt(now);
            repository.save(state);
        }

        boolean expired = clockRolledBack || now.isAfter(state.getExpiresAt());
        return LicenseStatusResponse.builder().expired(expired).expiresAt(state.getExpiresAt()).licenseKey(licenseKey).build();
    }

    @Override
    @Transactional
    public LicenseStatusResponse renew(Long storeId, String code) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("STORE_NOT_FOUND", "Store not found"));

        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(code)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new LocalizedException(HttpStatus.BAD_REQUEST, "LICENSE_CODE_INVALID", "Invalid or corrupted renewal code");
        }

        // Bound to Store.licenseKey (a random UUID), not the numeric storeId -
        // every customer runs their own separate, offline database, so every
        // customer's first store would otherwise get id=1, and a code bound to
        // that id would unlock every other customer's first store too.
        String codeLicenseKey = claims.get("licenseKey", String.class);
        if (codeLicenseKey == null || !codeLicenseKey.equals(store.getLicenseKey())) {
            throw new LocalizedException(HttpStatus.BAD_REQUEST, "LICENSE_CODE_INVALID", "This code was not issued for your store");
        }

        Date expiration = claims.getExpiration();
        if (expiration == null) {
            throw new LocalizedException(HttpStatus.BAD_REQUEST, "LICENSE_CODE_INVALID", "Renewal code is missing an expiry date");
        }

        LicenseState state = repository.findByStoreId(storeId)
                .orElseGet(() -> LicenseState.builder().store(store).build());
        state.setExpiresAt(expiration.toInstant());
        // A real renewal is a legitimate reference point - reset the watermark
        // to now so a rollback attempted before this renewal doesn't linger.
        state.setLastSeenAt(Instant.now());
        repository.save(state);

        log.info("License renewed for storeId {} - new expiry {}", storeId, expiration);
        return LicenseStatusResponse.builder().expired(false).expiresAt(expiration.toInstant()).licenseKey(store.getLicenseKey()).build();
    }
}
