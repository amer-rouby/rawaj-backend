package com.rawajsupermarket.settings.controller;

import com.rawajsupermarket.settings.dto.request.RawajFeatureSettingsRequest;
import com.rawajsupermarket.settings.dto.response.RawajFeatureSettingsResponse;
import com.rawajsupermarket.common.dto.ApiResponse;
import com.rawajsupermarket.settings.service.RawajFeatureSettingsService;
import com.rawajsupermarket.common.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings/rawaj-features")
@RequiredArgsConstructor
@Slf4j
public class RawajFeatureSettingsController {

    private final RawajFeatureSettingsService rawajFeatureSettingsService;

    // Any authenticated role can read the flags - every screen gated by one needs
    // to know whether to render itself, not just the settings screen. Only the
    // PUT below (the actual toggle) is ADMIN-only.
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<RawajFeatureSettingsResponse>> getSettings(
            @RequestParam Long storeId) {

        storeId = SecurityUtils.getCurrentStoreId();

        log.info("GET /api/settings/rawaj-features - storeId: {}", storeId);

        RawajFeatureSettingsResponse settings = rawajFeatureSettingsService.getSettings(storeId);
        return ResponseEntity.ok(ApiResponse.success(settings, "Rawaj feature settings retrieved successfully"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RawajFeatureSettingsResponse>> updateSettings(
            @RequestParam Long storeId,
            @Valid @RequestBody RawajFeatureSettingsRequest request) {

        storeId = SecurityUtils.getCurrentStoreId();

        log.info("PUT /api/settings/rawaj-features - storeId: {}", storeId);

        RawajFeatureSettingsResponse settings = rawajFeatureSettingsService.updateSettings(storeId, request);
        return ResponseEntity.ok(ApiResponse.success(settings, "Rawaj feature settings updated successfully"));
    }
}
