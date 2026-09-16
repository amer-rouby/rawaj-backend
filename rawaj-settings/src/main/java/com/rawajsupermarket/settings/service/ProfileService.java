package com.rawajsupermarket.settings.service;

import com.rawajsupermarket.settings.dto.response.ProfileResponse;
import com.rawajsupermarket.settings.dto.request.ProfileUpdateRequest;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);
    ProfileResponse updateProfile(Long userId, ProfileUpdateRequest request);
    ProfileResponse changePassword(Long userId, String oldPassword, String newPassword);

    void updateProfileImageUrl(Long userId, String imageUrl);
}
