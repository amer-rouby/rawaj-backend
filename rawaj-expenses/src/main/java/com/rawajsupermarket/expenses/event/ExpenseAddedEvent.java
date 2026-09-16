package com.rawajsupermarket.expenses.event;

import java.math.BigDecimal;

public record ExpenseAddedEvent(Long storeId, Long expenseId, BigDecimal amount) {
}
