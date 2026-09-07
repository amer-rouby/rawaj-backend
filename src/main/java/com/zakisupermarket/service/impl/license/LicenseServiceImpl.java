package com.zakisupermarket.service.impl.license;

import com.zakisupermarket.dto.license.response.LicenseStatusResponse;
import com.zakisupermarket.entity.Store;
import com.zakisupermarket.entity.license.LicenseState;
import com.zakisupermarket.exception.LocalizedException;
import com.zakisupermarket.exception.ResourceNotFoundException;
import com.zakisupermarket.repository.StoreRepository;
import com.zakisupermarket.repository.license.LicenseStateRepository;
import com.zakisupermarket.service.license.LicenseService;
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
    @Transactional(readOnly = true)
    public LicenseStatusResponse getStatus(Long storeId) {
        return repository.findByStoreId(storeId)
                .map(state -> LicenseStatusResponse.builder()
                        .expired(Instant.now().isAfter(state.getExpiresAt()))
                        .expiresAt(state.getExpiresAt())
                        .build())
                // No row yet = never licensed = unlimited, not locked. Keeps this
                // feature from retroactively locking out any existing store the
                // moment it ships, until you deliberately send that store a code.
                .orElseGet(() -> LicenseStatusResponse.builder().expired(false).expiresAt(null).build());
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
        repository.save(state);

        log.info("License renewed for storeId {} - new expiry {}", storeId, expiration);
        return LicenseStatusResponse.builder().expired(false).expiresAt(expiration.toInstant()).build();
    }
}
