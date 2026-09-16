package com.rawajsupermarket.service;

import com.rawajsupermarket.dto.response.DashboardResponse;
import com.rawajsupermarket.dto.response.RawajInsightsDTO;

public interface DashboardService {

    DashboardResponse getDashboardStats(Long storeId);

    RawajInsightsDTO getRawajInsights(Long storeId);
}