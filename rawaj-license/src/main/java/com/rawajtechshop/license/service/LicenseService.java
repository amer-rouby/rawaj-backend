package com.rawajtechshop.license.service;

import com.rawajtechshop.license.dto.response.LicenseStatusResponse;

public interface LicenseService {
    LicenseStatusResponse getStatus(Long storeId);

    LicenseStatusResponse renew(Long storeId, String code);
}
