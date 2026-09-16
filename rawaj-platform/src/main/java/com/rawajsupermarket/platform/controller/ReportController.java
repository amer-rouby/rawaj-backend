package com.rawajsupermarket.platform.controller;

import com.rawajsupermarket.common.dto.ApiResponse;
import com.rawajsupermarket.platform.dto.response.ExpiryReportResponse;
import com.rawajsupermarket.platform.dto.response.FinancialReportResponse;
import com.rawajsupermarket.platform.dto.request.ReportRequest;
import com.rawajsupermarket.platform.service.ReportService;
import com.rawajsupermarket.sales.dto.response.SalesReportResponse;
import com.rawajsupermarket.common.util.SecurityUtils;
import com.rawajsupermarket.platform.dto.response.StockReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/sales")
    public ResponseEntity<ApiResponse<SalesReportResponse>> getSalesReport(
            @RequestBody ReportRequest request) {
        request.setStoreId(SecurityUtils.getCurrentStoreId());
        SalesReportResponse response = reportService.getSalesReport(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/stock")
    public ResponseEntity<ApiResponse<StockReportResponse>> getStockReport(
            @RequestBody ReportRequest request) {
        request.setStoreId(SecurityUtils.getCurrentStoreId());
        StockReportResponse response = reportService.getStockReport(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/financial")
    public ResponseEntity<ApiResponse<FinancialReportResponse>> getFinancialReport(
            @RequestBody ReportRequest request) {
        request.setStoreId(SecurityUtils.getCurrentStoreId());
        FinancialReportResponse response = reportService.getFinancialReport(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/expiry")
    public ResponseEntity<ApiResponse<ExpiryReportResponse>> getExpiryReport(
            @RequestBody ReportRequest request) {
        request.setStoreId(SecurityUtils.getCurrentStoreId());
        ExpiryReportResponse response = reportService.getExpiryReport(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
