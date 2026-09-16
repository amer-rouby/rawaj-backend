package com.rawajsupermarket.settings.service.impl;

import com.rawajsupermarket.settings.entity.RawajFeatureSettings;
import com.rawajsupermarket.settings.repository.RawajFeatureSettingsRepository;
import com.rawajsupermarket.settings.dto.request.RawajFeatureSettingsRequest;
import com.rawajsupermarket.settings.dto.response.RawajFeatureSettingsResponse;
import com.rawajsupermarket.settings.service.RawajFeatureSettingsService;
import com.rawajsupermarket.common.entity.Store;
import com.rawajsupermarket.common.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RawajFeatureSettingsServiceImpl implements RawajFeatureSettingsService {

    private final RawajFeatureSettingsRepository settingsRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional(readOnly = true)
    public RawajFeatureSettingsResponse getSettings(Long storeId) {
        return RawajFeatureSettingsResponse.fromEntity(getOrCreate(storeId));
    }

    @Override
    @Transactional
    public RawajFeatureSettingsResponse updateSettings(Long storeId, RawajFeatureSettingsRequest request) {
        RawajFeatureSettings settings = getOrCreate(storeId);

        if (request.getStockPredictionEnabled() != null) {
            settings.setStockPredictionEnabled(request.getStockPredictionEnabled());
        }
        if (request.getReorderRecommendationsEnabled() != null) {
            settings.setReorderRecommendationsEnabled(request.getReorderRecommendationsEnabled());
        }
        if (request.getPricingRecommendationsEnabled() != null) {
            settings.setPricingRecommendationsEnabled(request.getPricingRecommendationsEnabled());
        }
        if (request.getSupplierRecommendationsEnabled() != null) {
            settings.setSupplierRecommendationsEnabled(request.getSupplierRecommendationsEnabled());
        }
        if (request.getDashboardInsightsEnabled() != null) {
            settings.setDashboardInsightsEnabled(request.getDashboardInsightsEnabled());
        }
        if (request.getDailyBriefEnabled() != null) {
            settings.setDailyBriefEnabled(request.getDailyBriefEnabled());
        }
        if (request.getAnomalyDetectionEnabled() != null) {
            settings.setAnomalyDetectionEnabled(request.getAnomalyDetectionEnabled());
        }
        if (request.getRealtimeUpdatesEnabled() != null) {
            settings.setRealtimeUpdatesEnabled(request.getRealtimeUpdatesEnabled());
        }
        if (request.getVoiceSearchEnabled() != null) {
            settings.setVoiceSearchEnabled(request.getVoiceSearchEnabled());
        }
        if (request.getCustomerCreditEnabled() != null) {
            settings.setCustomerCreditEnabled(request.getCustomerCreditEnabled());
        }
        if (request.getAiAssistantEnabled() != null) {
            settings.setAiAssistantEnabled(request.getAiAssistantEnabled());
        }
        if (request.getEInvoiceEnabled() != null) {
            settings.setEInvoiceEnabled(request.getEInvoiceEnabled());
        }
        if (request.getOfflineModeEnabled() != null) {
            settings.setOfflineModeEnabled(request.getOfflineModeEnabled());
        }
        if (request.getEmailEnabled() != null) {
            settings.setEmailEnabled(request.getEmailEnabled());
        }

        RawajFeatureSettings saved = settingsRepository.save(settings);
        log.info("Rawaj feature settings updated for storeId: {}", storeId);
        return RawajFeatureSettingsResponse.fromEntity(saved);
    }

    @Override
    @Transactional
    public RawajFeatureSettings getOrCreate(Long storeId) {
        return settingsRepository.findByStoreId(storeId)
                .orElseGet(() -> buildDefaultSettings(storeId));
    }

    // Deliberately not persisted here - getSettings() is read-only, and a plain
    // GET shouldn't have a write side effect. The transient defaults get saved
    // for real the first time updateSettings() actually runs.
    private RawajFeatureSettings buildDefaultSettings(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        return RawajFeatureSettings.builder()
                .store(store)
                .build();
    }
}
