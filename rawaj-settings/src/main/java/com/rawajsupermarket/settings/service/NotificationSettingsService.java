package com.rawajsupermarket.settings.service;

import com.rawajsupermarket.settings.dto.request.NotificationSettingsRequest;
import com.rawajsupermarket.settings.dto.response.NotificationSettingsResponse;

public interface NotificationSettingsService {

    NotificationSettingsResponse getSettings(Long userId);

    NotificationSettingsResponse updateSettings(Long userId, NotificationSettingsRequest request);
}