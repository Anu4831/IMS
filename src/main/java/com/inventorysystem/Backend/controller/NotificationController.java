package com.inventorysystem.Backend.controller;

import com.inventorysystem.Backend.dto.Notification.NotificationDTO;
import com.inventorysystem.Backend.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Endpoint to fetch unread notifications
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications() {
        try {
            List<NotificationDTO> unreadNotifications = notificationService.getUnreadNotifications();
            return ResponseEntity.ok(unreadNotifications);
        } catch (Exception e) {
            // Log the error (optional)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to mark a notification as read
    @PostMapping("/markAsRead/{id}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable("id") Long id) {
        try {
            notificationService.markAsRead(id);
            return ResponseEntity.ok("SUCCESS");
        } catch (EntityNotFoundException e) {
            // If notification is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error marking notification as read");
        }
    }
}
