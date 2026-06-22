package org.wavemoney.notification.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wavemoney.notification.api.entity.Notification;
import org.wavemoney.notification.api.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{phone}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable String phone) {
        List<Notification> notifications = notificationService.getNotifications(phone);
        return ResponseEntity.ok(notifications);
    }

}
