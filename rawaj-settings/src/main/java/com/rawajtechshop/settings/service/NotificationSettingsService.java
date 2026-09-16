package com.rawajtechshop.settings.service;

import com.rawajtechshop.settings.dto.request.NotificationSettingsRequest;
import com.rawajtechshop.settings.dto.response.NotificationSettingsResponse;

public interface NotificationSettingsService {

    NotificationSettingsResponse getSettings(Long userId);

    NotificationSettingsResponse updateSettings(Long userId, NotificationSettingsRequest request);
}
