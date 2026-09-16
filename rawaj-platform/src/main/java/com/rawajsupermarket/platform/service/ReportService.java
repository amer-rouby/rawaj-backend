package com.rawajsupermarket.platform.service;

import com.rawajsupermarket.platform.dto.response.ExpiryReportResponse;
import com.rawajsupermarket.platform.dto.response.FinancialReportResponse;
import com.rawajsupermarket.platform.dto.request.ReportRequest;
import com.rawajsupermarket.sales.dto.response.SalesReportResponse;
import com.rawajsupermarket.platform.dto.response.StockReportResponse;

public interface ReportService {
    SalesReportResponse getSalesReport(ReportRequest request);
    StockReportResponse getStockReport(ReportRequest request);
    FinancialReportResponse getFinancialReport(ReportRequest request);
    ExpiryReportResponse getExpiryReport(ReportRequest request);
}
