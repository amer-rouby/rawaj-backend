package com.rawajtechshop.platform.service;

import com.rawajtechshop.platform.dto.response.DashboardResponse;
import com.rawajtechshop.platform.dto.response.RawajInsightsDTO;

public interface DashboardService {

    DashboardResponse getDashboardStats(Long storeId);

    RawajInsightsDTO getRawajInsights(Long storeId);
}
