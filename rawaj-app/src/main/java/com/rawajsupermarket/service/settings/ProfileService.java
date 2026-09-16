package com.rawajsupermarket.service.settings;

import com.rawajsupermarket.dto.settings.request.ProfileUpdateRequest;
import com.rawajsupermarket.dto.settings.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);
    ProfileResponse updateProfile(Long userId, ProfileUpdateRequest request);
    ProfileResponse changePassword(Long userId, String oldPassword, String newPassword);

    void updateProfileImageUrl(Long userId, String imageUrl);
}