package com.rawajsupermarket.service.impl;

import com.rawajsupermarket.dto.response.AssistantAnswer;
import com.rawajsupermarket.exception.FeatureDisabledException;
import com.rawajsupermarket.service.AssistantProvider;
import com.rawajsupermarket.service.AssistantService;
import com.rawajsupermarket.service.settings.RawajFeatureSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private final AssistantProvider assistantProvider;
    private final RawajFeatureSettingsService rawajFeatureSettingsService;

    @Override
    @Transactional(readOnly = true)
    public AssistantAnswer ask(String query, Long storeId) {
        Boolean enabled = rawajFeatureSettingsService.getOrCreate(storeId).getAiAssistantEnabled();
        if (enabled != null && !enabled) {
            throw new FeatureDisabledException("FEATURE_DISABLED_AI_ASSISTANT", "Rawaj assistant feature is disabled for this store");
        }
        if (query == null || query.trim().isEmpty()) {
            throw new RuntimeException("Question cannot be empty");
        }
        return assistantProvider.answer(query, storeId);
    }
}
