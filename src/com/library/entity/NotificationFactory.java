package com.library.entity;

import com.library.constant.NotificationType;
import com.library.interfaces.NotificationStrategy;

public class NotificationFactory {

    public static NotificationStrategy getNotificationStrategy(NotificationType notificationType) {
        return switch (notificationType) {
            case SMS -> new SMSNotification();
            case EMAIL -> new EmailNotification();
        };
    }

}
