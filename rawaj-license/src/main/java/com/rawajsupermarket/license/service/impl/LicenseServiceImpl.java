package com.rawajsupermarket.license.service.impl;

import com.rawajsupermarket.license.dto.response.LicenseGenerateResponse;
import com.rawajsupermarket.license.dto.response.LicenseStatusResponse;
import com.rawajsupermarket.common.entity.Store;
import com.rawajsupermarket.license.entity.LicenseState;
import com.rawajsupermarket.common.exception.LocalizedException;
import com.rawajsupermarket.common.exception.ResourceNotFoundException;
import com.rawajsupermarket.common.repository.StoreRepository;
import com.rawajsupermarket.license.repository.LicenseStateRepository;
import com.rawajsupermarket.license.service.LicenseService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
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

    // Blank by default so this fails closed on any instance where it wasn't
    // deliberately set - only this vendor-only, never-shipped branch/instance
    // should ever have this pointed at a real private key file.
    @Value("${license.private-key-path:}")
    private String privateKeyPath;

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
        LicenseState state = repository.findByStoreId(storeId).orElse(null);
        if (state == null) {
            // No row yet = never licensed = unlimited, not locked. Keeps this
            // feature from retroactively locking out any existing store the
            // moment it ships, until you deliberately send that store a code.
            return LicenseStatusResponse.builder().expired(false).expiresAt(null).build();
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
        return LicenseStatusResponse.builder().expired(expired).expiresAt(state.getExpiresAt()).build();
    }

    @Override
    @Transactional
    public LicenseStatusResponse renew(Long storeId, String code) {
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

        Long codeStoreId = claims.get("storeId", Long.class);
        if (codeStoreId == null || !codeStoreId.equals(storeId)) {
            throw new LocalizedException(HttpStatus.BAD_REQUEST, "LICENSE_CODE_INVALID", "This code was not issued for your store");
        }

        Date expiration = claims.getExpiration();
        if (expiration == null) {
            throw new LocalizedException(HttpStatus.BAD_REQUEST, "LICENSE_CODE_INVALID", "Renewal code is missing an expiry date");
        }

        LicenseState state = repository.findByStoreId(storeId).orElseGet(() -> {
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("STORE_NOT_FOUND", "Store not found"));
            return LicenseState.builder().store(store).build();
        });
        state.setExpiresAt(expiration.toInstant());
        // A real renewal is a legitimate reference point - reset the watermark
        // to now so a rollback attempted before this renewal doesn't linger.
        state.setLastSeenAt(Instant.now());
        repository.save(state);

        log.info("License renewed for storeId {} - new expiry {}", storeId, expiration);
        return LicenseStatusResponse.builder().expired(false).expiresAt(expiration.toInstant()).build();
    }

    @Override
    public LicenseGenerateResponse generateCode(String licenseKey, int months) {
        if (privateKeyPath == null || privateKeyPath.isBlank()) {
            throw new LocalizedException(HttpStatus.SERVICE_UNAVAILABLE, "LICENSE_GENERATION_NOT_CONFIGURED",
                    "license.private-key-path is not set - this instance cannot generate codes");
        }

        PrivateKey privateKey;
        try {
            String pem = Files.readString(Path.of(privateKeyPath), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(pem);
            privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        } catch (Exception e) {
            throw new IllegalStateException("Could not load license private key from " + privateKeyPath, e);
        }

        Instant expiresAt = Instant.now().atZone(ZoneId.systemDefault()).plusMonths(months).toInstant();

        String code = Jwts.builder()
                .claim("licenseKey", licenseKey)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expiresAt))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        log.info("Generated activation code for licenseKey {} - expires {}", licenseKey, expiresAt);
        return LicenseGenerateResponse.builder().code(code).licenseKey(licenseKey).expiresAt(expiresAt).build();
    }
}
