package com.rawajtechshop.notification;

import com.rawajtechshop.expenses.event.ExpenseAddedEvent;
import com.rawajtechshop.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ExpenseAddedEventListener {

    private final NotificationService notificationService;

    @EventListener
    void onExpenseAdded(ExpenseAddedEvent event) {
        notificationService.notifyExpenseAdded(event.storeId(), event.expenseId(), event.amount());
    }
}
