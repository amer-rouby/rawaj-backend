package com.rawajsupermarket.service;

import com.rawajsupermarket.dto.request.ReportRequest;
import com.rawajsupermarket.dto.response.*;

public interface ReportService {
    SalesReportResponse getSalesReport(ReportRequest request);
    StockReportResponse getStockReport(ReportRequest request);
    FinancialReportResponse getFinancialReport(ReportRequest request);
    ExpiryReportResponse getExpiryReport(ReportRequest request);
}