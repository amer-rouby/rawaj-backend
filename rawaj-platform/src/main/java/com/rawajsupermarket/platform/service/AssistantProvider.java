package com.rawajsupermarket.platform.service;

import com.rawajsupermarket.platform.dto.response.AssistantAnswer;
import com.rawajsupermarket.platform.service.impl.RuleBasedAssistantProvider;

// Swap-point for a future real LLM-backed implementation, per the "0-cost for
// now" decision. RuleBasedAssistantProvider is the only implementation today -
// nothing else about the controller/service contract needs to change to add
// a real one later.
public interface AssistantProvider {
    AssistantAnswer answer(String query, Long storeId);
}
