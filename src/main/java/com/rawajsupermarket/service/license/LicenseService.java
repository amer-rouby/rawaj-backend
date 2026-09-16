package com.rawajsupermarket.service.license;

import com.rawajsupermarket.dto.license.response.LicenseStatusResponse;

public interface LicenseService {
    LicenseStatusResponse getStatus(Long storeId);

    LicenseStatusResponse renew(Long storeId, String code);
}
