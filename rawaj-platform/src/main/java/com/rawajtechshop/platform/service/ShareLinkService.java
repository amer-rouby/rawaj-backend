package com.rawajtechshop.platform.service;

import com.rawajtechshop.platform.dto.request.CreateShareLinkRequest;
import com.rawajtechshop.platform.entity.ShareLink;
import com.rawajtechshop.platform.dto.response.ShareLinkResponse;

public interface ShareLinkService {

    ShareLinkResponse createShareLink(CreateShareLinkRequest request, Long createdBy, Long storeId);

    ShareLink validateShareLink(String token);

    void incrementAccessCount(String token);

    void cleanupExpiredLinks();

    String buildShareUrl(ShareLink shareLink);
}
