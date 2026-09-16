package com.rawajsupermarket.settings.service;

import com.rawajsupermarket.settings.dto.request.StoreSettingsRequest;
import com.rawajsupermarket.settings.dto.response.StoreSettingsResponse;

public interface StoreSettingsService {

    StoreSettingsResponse getSettings(Long storeId);

    StoreSettingsResponse updateSettings(Long storeId, StoreSettingsRequest request);
}