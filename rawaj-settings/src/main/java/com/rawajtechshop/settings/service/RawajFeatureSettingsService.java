package com.rawajtechshop.settings.service;

import com.rawajtechshop.settings.entity.RawajFeatureSettings;
import com.rawajtechshop.settings.dto.request.RawajFeatureSettingsRequest;
import com.rawajtechshop.settings.dto.response.RawajFeatureSettingsResponse;

public interface RawajFeatureSettingsService {

    RawajFeatureSettingsResponse getSettings(Long storeId);

    RawajFeatureSettingsResponse updateSettings(Long storeId, RawajFeatureSettingsRequest request);

    // Used server-side by every Rawaj feature's own controller/service to fail
    // closed when its flag is off, not just to render the settings screen.
    RawajFeatureSettings getOrCreate(Long storeId);
}
