package com.rawajsupermarket.platform.service;

import com.rawajsupermarket.platform.dto.request.CreateShareLinkRequest;
import com.rawajsupermarket.platform.dto.response.ShareLinkResponse;
import com.rawajsupermarket.platform.entity.ShareLink;

public interface ShareLinkService {

    ShareLinkResponse createShareLink(CreateShareLinkRequest request, Long createdBy, Long storeId);

    ShareLink validateShareLink(String token);

    void incrementAccessCount(String token);

    void cleanupExpiredLinks();

    String buildShareUrl(ShareLink shareLink);
}