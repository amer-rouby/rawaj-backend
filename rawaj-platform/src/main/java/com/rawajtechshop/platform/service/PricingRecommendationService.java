package com.rawajtechshop.platform.service;

import com.rawajtechshop.platform.dto.response.PricingRecommendationDTO;

import java.util.List;

public interface PricingRecommendationService {
    List<PricingRecommendationDTO> getRecommendations(Long storeId);
}
