package com.inventorysystem.Backend.service.imp;

import com.inventorysystem.Backend.model.Article;
import com.inventorysystem.Backend.model.Notification;
import com.inventorysystem.Backend.repository.ArticleRepository;
import com.inventorysystem.Backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;


@Configuration
@EnableScheduling
public class SaveNotificationShechudler {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Scheduled(fixedRate  = 5000)

    public void SaveNotification(){

       List<Article> articleList = articleRepository.findArticlesByStockThreshold(3);
        System.out.println("atricle:------------------------------   "+articleList.size());

        articleList.forEach(article -> {

            if (notificationRepository.countNotificationByArticleId(article.getArticleId()) == 0){

                Notification notification = new Notification();
                notification.setName(article.getName());
                notification.setMessage("Stock is getting low of "+article.getName());
                notification.setDateTime(LocalDateTime.now());

                notificationRepository.save(notification);
            }
        });
    }


}
