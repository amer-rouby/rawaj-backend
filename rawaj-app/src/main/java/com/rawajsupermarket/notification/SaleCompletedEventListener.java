package com.rawajsupermarket.notification;

import com.rawajsupermarket.sales.event.SaleCompletedEvent;
import com.rawajsupermarket.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SaleCompletedEventListener {

    private final NotificationService notificationService;

    @EventListener
    void onSaleCompleted(SaleCompletedEvent event) {
        notificationService.notifySaleCompleted(event.storeId(), event.saleId(), event.totalAmount());
    }
}
