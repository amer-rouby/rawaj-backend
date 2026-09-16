package com.rawajsupermarket.service.settings;

import com.rawajsupermarket.dto.settings.request.StoreSettingsRequest;
import com.rawajsupermarket.dto.settings.response.StoreSettingsResponse;

public interface StoreSettingsService {

    StoreSettingsResponse getSettings(Long storeId);

    StoreSettingsResponse updateSettings(Long storeId, StoreSettingsRequest request);
}