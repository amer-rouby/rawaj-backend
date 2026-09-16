package com.rawajsupermarket.license.controller;

import com.rawajsupermarket.license.dto.request.LicenseGenerateRequest;
import com.rawajsupermarket.license.dto.request.LicenseRenewRequest;
import com.rawajsupermarket.license.dto.response.LicenseGenerateResponse;
import com.rawajsupermarket.license.dto.response.LicenseStatusResponse;
import com.rawajsupermarket.common.dto.ApiResponse;
import com.rawajsupermarket.license.service.LicenseService;
import com.rawajsupermarket.common.util.SecurityUtils;
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

    // Vendor-only: signs a new code with the private key. Only meaningful on
    // this internal, never-shipped instance (license.private-key-path unset
    // everywhere else, so this fails closed on any customer-facing build).
    @PostMapping("/generate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LicenseGenerateResponse>> generate(@Valid @RequestBody LicenseGenerateRequest request) {
        log.info("POST /api/license/generate - licenseKey: {}", request.getLicenseKey());
        LicenseGenerateResponse response = licenseService.generateCode(request.getLicenseKey(), request.getMonths());
        return ResponseEntity.ok(ApiResponse.success(response, "Activation code generated"));
    }
}
