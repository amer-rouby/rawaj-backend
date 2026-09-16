package com.rawajsupermarket.notification;

import com.rawajsupermarket.settings.event.SecurityAlertEvent;
import com.rawajsupermarket.service.NotificationService;
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
