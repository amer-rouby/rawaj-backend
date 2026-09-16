package com.rawajsupermarket.service.settings;

import com.rawajsupermarket.dto.settings.request.RawajFeatureSettingsRequest;
import com.rawajsupermarket.dto.settings.response.RawajFeatureSettingsResponse;
import com.rawajsupermarket.entity.settings.RawajFeatureSettings;

public interface RawajFeatureSettingsService {

    RawajFeatureSettingsResponse getSettings(Long storeId);

    RawajFeatureSettingsResponse updateSettings(Long storeId, RawajFeatureSettingsRequest request);

    // Used server-side by every Rawaj feature's own controller/service to fail
    // closed when its flag is off, not just to render the settings screen.
    RawajFeatureSettings getOrCreate(Long storeId);
}
