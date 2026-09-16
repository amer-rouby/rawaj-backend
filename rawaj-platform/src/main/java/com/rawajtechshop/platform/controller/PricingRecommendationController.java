package com.rawajtechshop.platform.controller;

import com.rawajtechshop.common.dto.ApiResponse;
import com.rawajtechshop.common.exception.FeatureDisabledException;
import com.rawajtechshop.platform.dto.response.PricingRecommendationDTO;
import com.rawajtechshop.platform.service.PricingRecommendationService;
import com.rawajtechshop.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pricing-recommendations")
@RequiredArgsConstructor
@Slf4j
public class PricingRecommendationController {

    private final PricingRecommendationService pricingRecommendationService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<PricingRecommendationDTO>>> getRecommendations() {
        Long storeId = SecurityUtils.getCurrentStoreId();
        try {
            List<PricingRecommendationDTO> recommendations = pricingRecommendationService.getRecommendations(storeId);
            return ResponseEntity.ok(ApiResponse.success(recommendations));
        } catch (FeatureDisabledException e) {
            return ResponseEntity.status(403).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting pricing recommendations", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Failed to get pricing recommendations: " + e.getMessage()));
        }
    }
}
