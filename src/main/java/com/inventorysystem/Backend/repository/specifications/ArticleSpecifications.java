package com.inventorysystem.Backend.repository.specifications;

import com.inventorysystem.Backend.model.Article;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecifications {

    public static Specification<Article> searchArticles(String searchTerm) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.equal(root.get("articleId").as(String.class), searchTerm),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + searchTerm.toLowerCase() + "%")
        );
    }



    // Collaborative filtering: Find similar articles based on item features
    public static Specification<Article> similarArticles(Article currentArticle) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                // Example similarity criteria: same brand or similar name
                criteriaBuilder.equal(root.get("brand"), currentArticle.getBrand()),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + currentArticle.getName().toLowerCase() + "%")
        );
    }

}
