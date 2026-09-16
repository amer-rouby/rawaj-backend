package com.rawajtechshop.settings.service;

import com.rawajtechshop.settings.dto.request.StoreSettingsRequest;
import com.rawajtechshop.settings.dto.response.StoreSettingsResponse;

public interface StoreSettingsService {

    StoreSettingsResponse getSettings(Long storeId);

    StoreSettingsResponse updateSettings(Long storeId, StoreSettingsRequest request);
}
