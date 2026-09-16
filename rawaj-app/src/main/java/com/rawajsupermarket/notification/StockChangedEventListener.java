package com.rawajsupermarket.notification;

import com.rawajsupermarket.catalog.event.StockChangedEvent;
import com.rawajsupermarket.notifications.service.NotificationStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
class StockChangedEventListener {

    private final NotificationStreamService notificationStreamService;

    @EventListener
    void onStockChanged(StockChangedEvent event) {
        notificationStreamService.notifyStockChanged(event.storeId(),
                Map.of("changeType", event.changeType(), "batch", event.batch()));
    }
}
