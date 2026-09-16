package com.rawajtechshop.notifications.dto.request;

import com.rawajtechshop.notifications.entity.Notification;
import com.rawajtechshop.common.entity.Store;
import com.rawajtechshop.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private Store store;
    private User recipient;
    private String title;
    private String message;
    private String titleEn;
    private String messageEn;
    private Notification.NotificationType type;
    private Notification.NotificationPriority priority;
    private String relatedEntityType;
    private Long relatedEntityId;

    public boolean isValid() {
        return store != null
                && title != null && !title.isBlank()
                && message != null && !message.isBlank()
                && type != null;
    }
}
