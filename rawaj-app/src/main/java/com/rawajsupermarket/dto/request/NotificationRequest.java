package com.rawajsupermarket.dto.request;

import com.rawajsupermarket.entity.Notification;
import com.rawajsupermarket.common.entity.Store;
import com.rawajsupermarket.common.entity.User;
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