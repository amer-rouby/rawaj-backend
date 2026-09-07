package com.zakisupermarket.service.license;

import com.zakisupermarket.dto.license.response.LicenseStatusResponse;

public interface LicenseService {
    LicenseStatusResponse getStatus(Long storeId);

    LicenseStatusResponse renew(Long storeId, String code);
}
