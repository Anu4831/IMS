package com.inventorysystem.Backend.repository;

import com.inventorysystem.Backend.dto.Notification.NotificationDTO;
import com.inventorysystem.Backend.model.Article;
import com.inventorysystem.Backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByIsRead(boolean isRead);  // Add a boolean parameter to specify the value

//    @Query(value = "Select count(n) FROM Notification n WHERE n.articleId = :articleId and n.isRead = false" )
//    Long countNotificationByArticleId(@Param("articleId") Long articleId);
// Count unread notifications for a specific article
@Query(value = "SELECT count(n) FROM Notification n WHERE n.articleId = :articleId AND n.isRead = false")
Long countNotificationByArticleId(@Param("articleId") Long articleId);

    // Fetch unread notifications with article name
    @Query("SELECT new com.inventorysystem.Backend.dto.Notification.NotificationDTO(" +
            "n.id, n.message, n.articleId, a.name, n.isRead, n.dateTime) " +
            "FROM Notification n " +
            "JOIN Article a ON n.articleId = a.articleId " +
            "WHERE n.isRead = false")
    List<NotificationDTO> findUnreadNotificationsWithArticleNames();

}
