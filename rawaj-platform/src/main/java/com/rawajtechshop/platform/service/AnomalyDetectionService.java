package com.rawajtechshop.platform.service;

import com.rawajtechshop.platform.entity.AnomalyDetection;
import com.rawajtechshop.platform.dto.response.AnomalyDetectionResponse;
import org.springframework.data.domain.Page;

public interface AnomalyDetectionService {

    void runDetectionForAllStores();

    void runDetectionForStore(Long storeId);

    Page<AnomalyDetectionResponse> getAnomalies(Long storeId, AnomalyDetection.Status status,
                                                  AnomalyDetection.Type type, int page, int size);

    long countByStatus(Long storeId, AnomalyDetection.Status status);

    AnomalyDetectionResponse markReviewed(Long id, Long storeId, Long userId);

    AnomalyDetectionResponse dismiss(Long id, Long storeId, Long userId);
}
