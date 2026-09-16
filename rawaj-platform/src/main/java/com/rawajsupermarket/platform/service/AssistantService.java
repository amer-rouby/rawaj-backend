package com.rawajsupermarket.platform.service;

import com.rawajsupermarket.platform.dto.response.AssistantAnswer;

public interface AssistantService {
    AssistantAnswer ask(String query, Long storeId);
}
