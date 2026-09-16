package com.rawajsupermarket.platform.service;

import com.rawajsupermarket.platform.dto.response.PricingRecommendationDTO;

import java.util.List;

public interface PricingRecommendationService {
    List<PricingRecommendationDTO> getRecommendations(Long storeId);
}
