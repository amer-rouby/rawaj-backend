package com.rawajtechshop.catalog.event;

import com.rawajtechshop.catalog.dto.response.StockBatchResponse;

public record StockChangedEvent(Long storeId, String changeType, StockBatchResponse batch) {
}
