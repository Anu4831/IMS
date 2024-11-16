package com.inventorysystem.Backend.service;

import com.inventorysystem.Backend.dto.Notification.NotificationDTO;
import java.util.List;

//public interface NotificationService {
//   // List<NotificationDTO> getUnreadNotification();
//    List<NotificationDTO> getUnreadNotifications();
////
//
//    void markAsRead(Long id);
//
//}
public interface NotificationService {
    List<NotificationDTO> getUnreadNotifications();
    void markAsRead(Long id);
}