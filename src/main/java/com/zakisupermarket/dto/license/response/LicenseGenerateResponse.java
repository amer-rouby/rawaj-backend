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
public class LicenseGenerateResponse {
    private String code;
    private String licenseKey;
    private Instant expiresAt;
}
