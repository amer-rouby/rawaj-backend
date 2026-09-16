package com.rawajtechshop.catalog.service;

import com.rawajtechshop.catalog.dto.response.StockAdjustmentHistoryDTO;
import com.rawajtechshop.catalog.dto.request.StockAdjustmentRequest;
import com.rawajtechshop.catalog.dto.request.StockBatchRequest;
import com.rawajtechshop.catalog.dto.response.StockBatchResponse;
import org.springframework.data.domain.Page;
import java.util.List;

public interface StockBatchService {
    Page<StockBatchResponse> getAllBatches(Long storeId, int page, int size);
    StockBatchResponse getBatch(Long id, Long storeId);
    StockBatchResponse createBatch(StockBatchRequest request, Long storeId, Long userId);
    StockBatchResponse updateBatch(Long id, StockBatchRequest request, Long storeId, Long userId);
    void deleteBatch(Long id, Long storeId, Long userId);
    List<StockBatchResponse> getExpiringBatches(Long storeId, int days);
    List<StockBatchResponse> getExpiredBatches(Long storeId);
    StockBatchResponse adjustStock(Long batchId, StockAdjustmentRequest request, Long userId, Long storeId);
    List<StockAdjustmentHistoryDTO> getAdjustmentHistory(Long batchId, Long storeId);
}
