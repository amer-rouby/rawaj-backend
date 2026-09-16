package com.rawajsupermarket.service.license;

import com.rawajsupermarket.dto.license.response.LicenseGenerateResponse;
import com.rawajsupermarket.dto.license.response.LicenseStatusResponse;

public interface LicenseService {
    LicenseStatusResponse getStatus(Long storeId);

    LicenseStatusResponse renew(Long storeId, String code);

    // Vendor-only: signs a new activation/renewal code with the private key.
    // Only meaningful on the internal, never-shipped instance that has
    // license.private-key-path configured - see LicenseServiceImpl.
    LicenseGenerateResponse generateCode(String licenseKey, int months);
}
