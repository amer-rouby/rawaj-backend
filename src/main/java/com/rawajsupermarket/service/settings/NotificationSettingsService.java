package com.rawajsupermarket.service.settings;

import com.rawajsupermarket.dto.settings.request.NotificationSettingsRequest;
import com.rawajsupermarket.dto.settings.response.NotificationSettingsResponse;

public interface NotificationSettingsService {

    NotificationSettingsResponse getSettings(Long userId);

    NotificationSettingsResponse updateSettings(Long userId, NotificationSettingsRequest request);
}