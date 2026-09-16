package com.rawajsupermarket.catalog.event;

import com.rawajsupermarket.catalog.dto.response.StockBatchResponse;

public record StockChangedEvent(Long storeId, String changeType, StockBatchResponse batch) {
}
