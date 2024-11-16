package com.inventorysystem.Backend.service.imp;

import com.inventorysystem.Backend.dto.Notification.NotificationDTO;
import com.inventorysystem.Backend.model.Notification;
import com.inventorysystem.Backend.repository.NotificationRepository;
import com.inventorysystem.Backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImp implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

//    @Override
//    public List<NotificationDTO> getUnreadNotifications() {
//        // Fetch only unread notifications
//        List<Notification> unreadNotifications = notificationRepository.findByIsRead(false);
//        // Convert to NotificationDTO
//        return unreadNotifications.stream()
//                .map(notification -> new NotificationDTO(
//                        notification.getId(),
//                        notification.getMessage(),// Map the articleName field
//                        notification.isRead()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void markAsRead(Long id) {
//        // Fetch the notification by ID or throw an exception
//        Notification notification = notificationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
//        // Set the notification as read
//        notification.setRead(true); // Mark as read (was incorrectly set to false earlier)
//        // Save the updated notification
//        notificationRepository.save(notification);
//    }


    @Override
    public List<NotificationDTO> getUnreadNotifications() {
        List<Notification> unreadNotifications = notificationRepository.findByIsRead(false);
        return unreadNotifications.stream()
                .map(notification -> new NotificationDTO(
                        notification.getId(),
                        notification.getMessage(),
                        notification.getArticleId(),
                        notification.getArticleName(),
                        notification.isRead(),
                        notification.getDateTime()))
                .collect(Collectors.toList());
    }



    @Override
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
        notification.setRead(true); // Properly sets the notification as read
        notificationRepository.save(notification);
    }
}
