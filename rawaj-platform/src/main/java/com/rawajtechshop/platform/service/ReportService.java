package com.rawajtechshop.platform.service;

import com.rawajtechshop.platform.dto.response.ExpiryReportResponse;
import com.rawajtechshop.platform.dto.response.FinancialReportResponse;
import com.rawajtechshop.platform.dto.request.ReportRequest;
import com.rawajtechshop.sales.dto.response.SalesReportResponse;
import com.rawajtechshop.platform.dto.response.StockReportResponse;

public interface ReportService {
    SalesReportResponse getSalesReport(ReportRequest request);
    StockReportResponse getStockReport(ReportRequest request);
    FinancialReportResponse getFinancialReport(ReportRequest request);
    ExpiryReportResponse getExpiryReport(ReportRequest request);
}
