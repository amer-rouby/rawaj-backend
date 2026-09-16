package com.rawajsupermarket.platform.service;

import com.rawajsupermarket.platform.dto.request.ReportRequest;
import com.rawajsupermarket.platform.dto.response.*;
import com.rawajsupermarket.sales.dto.response.SalesReportResponse;
import com.rawajsupermarket.common.dto.ApiResponse;

public interface ReportService {
    SalesReportResponse getSalesReport(ReportRequest request);
    StockReportResponse getStockReport(ReportRequest request);
    FinancialReportResponse getFinancialReport(ReportRequest request);
    ExpiryReportResponse getExpiryReport(ReportRequest request);
}