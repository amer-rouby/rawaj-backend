// src/main/java/com/rawajsupermarket/controller/DashboardController.java

package com.rawajsupermarket.controller;

import com.rawajsupermarket.common.dto.ApiResponse;
import com.rawajsupermarket.dto.response.DashboardResponse;
import com.rawajsupermarket.dto.response.RawajInsightsDTO;
import com.rawajsupermarket.common.exception.FeatureDisabledException;
import com.rawajsupermarket.service.DashboardService;
import com.rawajsupermarket.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST', 'MANAGER')")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboardStats(
            @RequestParam Long storeId) {

        storeId = SecurityUtils.getCurrentStoreId();

        log.info("GET /api/dashboard/stats - storeId: {}", storeId);

        DashboardResponse stats = dashboardService.getDashboardStats(storeId);

        return ResponseEntity.ok(ApiResponse.success(
                stats,
                "Dashboard stats retrieved successfully"
        ));
    }

    @GetMapping("/rawaj-insights")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST', 'MANAGER')")
    public ResponseEntity<ApiResponse<RawajInsightsDTO>> getRawajInsights() {
        Long storeId = SecurityUtils.getCurrentStoreId();
        try {
            RawajInsightsDTO insights = dashboardService.getRawajInsights(storeId);
            return ResponseEntity.ok(ApiResponse.success(insights, "Rawaj insights retrieved successfully"));
        } catch (FeatureDisabledException e) {
            return ResponseEntity.status(403).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting Rawaj insights", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Failed to get Rawaj insights: " + e.getMessage()));
        }
    }
}