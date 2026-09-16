package com.rawajsupermarket.license.service;

import com.rawajsupermarket.license.dto.response.LicenseStatusResponse;

public interface LicenseService {
    LicenseStatusResponse getStatus(Long storeId);

    LicenseStatusResponse renew(Long storeId, String code);
}
