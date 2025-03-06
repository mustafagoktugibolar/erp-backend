package com.archproj.erp_backend.observer;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements OrderObserver {
    @Override
    public void update(String message) {
        System.out.println("📧 Email sent: " + message);
    }
}
