package com.rawajsupermarket.platform.service;

import com.rawajsupermarket.platform.dto.response.DashboardResponse;
import com.rawajsupermarket.platform.dto.response.RawajInsightsDTO;

public interface DashboardService {

    DashboardResponse getDashboardStats(Long storeId);

    RawajInsightsDTO getRawajInsights(Long storeId);
}
