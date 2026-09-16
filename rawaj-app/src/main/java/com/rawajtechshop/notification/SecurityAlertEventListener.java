package com.rawajtechshop.notification;

import com.rawajtechshop.settings.event.SecurityAlertEvent;
import com.rawajtechshop.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SecurityAlertEventListener {

    private final NotificationService notificationService;

    @EventListener
    void onSecurityAlert(SecurityAlertEvent event) {
        notificationService.notifySecurityAlert(event.userId());
    }
}
