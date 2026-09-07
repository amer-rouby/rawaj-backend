package com.zakisupermarket.dto.license.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LicenseRenewRequest {
    @NotBlank
    private String code;
}
