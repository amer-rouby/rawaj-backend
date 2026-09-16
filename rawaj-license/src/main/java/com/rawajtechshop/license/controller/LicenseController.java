package com.rawajtechshop.license.controller;

import com.rawajtechshop.common.dto.ApiResponse;
import com.rawajtechshop.license.dto.request.LicenseRenewRequest;
import com.rawajtechshop.license.service.LicenseService;
import com.rawajtechshop.license.dto.response.LicenseStatusResponse;
import com.rawajtechshop.common.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/license")
@RequiredArgsConstructor
@Slf4j
public class LicenseController {

    private final LicenseService licenseService;

    // Any authenticated role can check status - the app needs to know whether
    // to lock itself down right after any user logs in, not just the admin.
    @GetMapping("/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LicenseStatusResponse>> getStatus() {
        Long storeId = SecurityUtils.getCurrentStoreId();
        return ResponseEntity.ok(ApiResponse.success(licenseService.getStatus(storeId)));
    }

    // ADMIN-only, same convention as other store-wide settings writes (e.g.
    // RawajFeatureSettingsController.updateSettings) - renewing isn't something
    // a cashier/staff account should be able to trigger.
    @PostMapping("/renew")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LicenseStatusResponse>> renew(@Valid @RequestBody LicenseRenewRequest request) {
        Long storeId = SecurityUtils.getCurrentStoreId();
        log.info("POST /api/license/renew - storeId: {}", storeId);
        LicenseStatusResponse status = licenseService.renew(storeId, request.getCode());
        return ResponseEntity.ok(ApiResponse.success(status, "Subscription renewed successfully"));
    }
}
