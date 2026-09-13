package com.zakisupermarket.dto.license.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenseStatusResponse {
    private boolean expired;
    // Null when the store has never been licensed yet (unlimited/not activated).
    private Instant expiresAt;
    // Shown on the renewal/activation screen so whoever is on-site can read it
    // off and send it to the vendor - the vendor has no other way to know which
    // store is asking, since this app never phones home and runs fully offline
    // on the customer's own machine. A random UUID (Store.licenseKey), not the
    // numeric store id - every customer has their own separate database, so
    // every customer's first store would otherwise get the same id (1).
    private String licenseKey;
}
