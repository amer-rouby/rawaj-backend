package com.rawajsupermarket.platform.service.impl;

import com.rawajsupermarket.platform.dto.response.AssistantAnswer;
import com.rawajsupermarket.common.exception.FeatureDisabledException;
import com.rawajsupermarket.platform.service.AssistantProvider;
import com.rawajsupermarket.platform.service.AssistantService;
import com.rawajsupermarket.settings.service.RawajFeatureSettingsService;
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
