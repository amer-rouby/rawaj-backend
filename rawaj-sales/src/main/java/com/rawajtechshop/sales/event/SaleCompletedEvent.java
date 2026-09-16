package com.rawajtechshop.sales.event;

import java.math.BigDecimal;

public record SaleCompletedEvent(Long storeId, Long saleId, BigDecimal totalAmount) {
}
