package com.rawajtechshop.platform.service;

import com.rawajtechshop.platform.dto.response.AssistantAnswer;

public interface AssistantService {
    AssistantAnswer ask(String query, Long storeId);
}
