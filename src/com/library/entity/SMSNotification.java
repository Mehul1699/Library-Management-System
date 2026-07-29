package com.library.entity;

import com.library.interfaces.NotificationStrategy;

public class SMSNotification implements NotificationStrategy {
    @Override
    public void sendNotification(String message) {
        System.out.println("SMS " + message);
    }
}
