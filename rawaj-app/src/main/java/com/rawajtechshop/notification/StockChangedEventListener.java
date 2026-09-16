package com.rawajtechshop.notification;

import com.rawajtechshop.catalog.event.StockChangedEvent;
import com.rawajtechshop.notifications.service.NotificationStreamService;
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
