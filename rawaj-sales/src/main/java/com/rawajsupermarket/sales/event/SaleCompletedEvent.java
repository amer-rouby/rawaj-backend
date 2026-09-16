package com.rawajsupermarket.sales.event;

import java.math.BigDecimal;

public record SaleCompletedEvent(Long storeId, Long saleId, BigDecimal totalAmount) {
}
