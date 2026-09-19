package com.rawajsupermarket.license.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LicenseGenerateRequest {
    @NotBlank
    private String licenseKey;

    // Both default to 0 and add together (e.g. months=0, days=10 for a short
    // trial) - validated as "at least one must be positive" in the service,
    // not here, since neither field alone can express that on its own.
    @Min(0)
    private int months;

    @Min(0)
    private int days;
}
