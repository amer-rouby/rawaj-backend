package com.rawajsupermarket.settings.service;

import com.rawajsupermarket.settings.entity.RawajFeatureSettings;
import com.rawajsupermarket.settings.dto.request.RawajFeatureSettingsRequest;
import com.rawajsupermarket.settings.dto.response.RawajFeatureSettingsResponse;

public interface RawajFeatureSettingsService {

    RawajFeatureSettingsResponse getSettings(Long storeId);

    RawajFeatureSettingsResponse updateSettings(Long storeId, RawajFeatureSettingsRequest request);

    // Used server-side by every Rawaj feature's own controller/service to fail
    // closed when its flag is off, not just to render the settings screen.
    RawajFeatureSettings getOrCreate(Long storeId);
}
