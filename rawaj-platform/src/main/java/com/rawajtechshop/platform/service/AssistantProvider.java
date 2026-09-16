package com.rawajtechshop.platform.service;

import com.rawajtechshop.platform.dto.response.AssistantAnswer;
import com.rawajtechshop.platform.service.impl.RuleBasedAssistantProvider;

// Swap-point for a future real LLM-backed implementation, per the "0-cost for
// now" decision. RuleBasedAssistantProvider is the only implementation today -
// nothing else about the controller/service contract needs to change to add
// a real one later.
public interface AssistantProvider {
    AssistantAnswer answer(String query, Long storeId);
}
