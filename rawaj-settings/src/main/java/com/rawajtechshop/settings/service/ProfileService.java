package com.rawajtechshop.settings.service;

import com.rawajtechshop.settings.dto.response.ProfileResponse;
import com.rawajtechshop.settings.dto.request.ProfileUpdateRequest;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);
    ProfileResponse updateProfile(Long userId, ProfileUpdateRequest request);
    ProfileResponse changePassword(Long userId, String oldPassword, String newPassword);

    void updateProfileImageUrl(Long userId, String imageUrl);
}
