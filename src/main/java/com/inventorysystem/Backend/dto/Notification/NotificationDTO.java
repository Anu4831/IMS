package com.inventorysystem.Backend.dto.Notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class NotificationDTO {

    private Long id;
    private String message;
    private Long articleId;
    private String name;
    private boolean isRead;
    private LocalDateTime dateTime;
    public String getArticleName() {
        return name;
    }
}
