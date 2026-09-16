package com.rawajsupermarket.service;

import com.rawajsupermarket.dto.response.AssistantAnswer;

public interface AssistantService {
    AssistantAnswer ask(String query, Long storeId);
}
