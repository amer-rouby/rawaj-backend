package com.zakisupermarket.dto.license.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LicenseGenerateRequest {
    @NotBlank
    private String licenseKey;

    @Min(1)
    private int months;
}
