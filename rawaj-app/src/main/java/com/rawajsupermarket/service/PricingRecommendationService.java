package com.rawajsupermarket.service;

import com.rawajsupermarket.dto.response.PricingRecommendationDTO;

import java.util.List;

public interface PricingRecommendationService {
    List<PricingRecommendationDTO> getRecommendations(Long storeId);
}
