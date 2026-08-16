package com.library.entity;

import com.library.interfaces.NotificationStrategy;

public class EmailNotification implements NotificationStrategy {
    @Override
    public void sendNotification(String message) {
        System.out.println("Email " + message);
    }
}
