package com.rawajsupermarket.service;

import com.rawajsupermarket.dto.request.CreateShareLinkRequest;
import com.rawajsupermarket.dto.response.ShareLinkResponse;
import com.rawajsupermarket.entity.ShareLink;

public interface ShareLinkService {

    ShareLinkResponse createShareLink(CreateShareLinkRequest request, Long createdBy, Long storeId);

    ShareLink validateShareLink(String token);

    void incrementAccessCount(String token);

    void cleanupExpiredLinks();

    String buildShareUrl(ShareLink shareLink);
}