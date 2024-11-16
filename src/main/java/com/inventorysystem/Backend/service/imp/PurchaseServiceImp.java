package com.inventorysystem.Backend.service.imp;

import com.inventorysystem.Backend.dto.purchase.*;
import com.inventorysystem.Backend.mapper.PurchaseMapper;
import com.inventorysystem.Backend.model.Article;
import com.inventorysystem.Backend.model.Purchase;
import com.inventorysystem.Backend.model.PurchaseDetail;
import com.inventorysystem.Backend.repository.ArticleRepository;
import com.inventorysystem.Backend.repository.PurchaseDetailRepository;
import com.inventorysystem.Backend.repository.PurchaseRepository;
import com.inventorysystem.Backend.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImp implements PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseDetailRepository purchaseDetailRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    PurchaseMapper purchaseMapper;

    @Override
    @Transactional
    public PurchaseDetailDTO createPurchase(PurchaseCreationDTO purchase) {
        if (purchase.getArticles().isEmpty()) {
            System.out.println("Purchase cannot be empty");
            return null;
        }

        List<PurchaseCreationArticleDTO> validArticles = new ArrayList<>();
        Long totalPurchasePrice = 0L;

        // Validate articles and calculate the total purchase price
        for (PurchaseCreationArticleDTO article : purchase.getArticles()) {
            if (!articleRepository.existsById(article.getArticleId()) || article.getArticleQuantity() < 1) {
                continue;
            }

            // Retrieve the article from the repository
            Article foundArticle = articleRepository.getArticleById(article.getArticleId());

            // Validate if the item belongs to the selected provider
            if (foundArticle.getProviderId() != purchase.getProviderId()) {
                continue;
            }

            // Add valid article to the list
            validArticles.add(article);

            // Add price to the total value of the purchase
            totalPurchasePrice += foundArticle.getPurchasePrice() * article.getArticleQuantity();
        }

        if (validArticles.isEmpty()) {
            System.out.println("No valid articles found");
            return null;
        }

        // Create and save the Purchase entity
        Purchase purchaseEntity = new Purchase();
        purchaseEntity.setProviderId(purchase.getProviderId());
        purchaseEntity.setUserId(purchase.getSessionUserId());
        purchaseEntity.setTotalValue(totalPurchasePrice.intValue());

        // Save the purchase and get the generated ID
        Purchase savedPurchase = purchaseRepository.save(purchaseEntity);
        Long newPurchaseId = savedPurchase.getPurchaseId();

        // Handle purchase details
        for (PurchaseCreationArticleDTO article : validArticles) {
            Article foundArticle = articleRepository.getArticleById(article.getArticleId());
            Long totalValue = (long) (foundArticle.getPurchasePrice() * article.getArticleQuantity());

            // Create and save purchase detail
            purchaseDetailRepository.createPurchaseDetail(
                    newPurchaseId,
                    foundArticle.getArticleId(),
                    article.getArticleQuantity(),
                    totalValue
            );

            // Update article stock
            Integer stock = foundArticle.getStock() + article.getArticleQuantity();
            foundArticle.setStock(stock);
            articleRepository.save(foundArticle);
        }

        // Return the created purchase details
        return getPurchaseById(newPurchaseId);
    }

    @Override
    @Transactional
    public PurchasesPageDTO getAllPurchases(String criteria, Integer page, Integer pageSize) {
        PurchasesPageDTO pagedPurchasesResponse = new PurchasesPageDTO();
        Page<Purchase> purchasePage;

        if (criteria == null || criteria.isEmpty()) {
            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("purchaseId").descending());
            purchasePage = purchaseRepository.findAll(pageable);
        } else {
            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("purchase_id").descending()); // purchase_id because it's a native query
            purchasePage = purchaseRepository.findAllPurchases(criteria, pageable);
        }

        List<PurchaseDTO> purchases = purchasePage.getContent().stream()
                .map(purchase -> purchaseMapper.purchaseToDTO(purchase))
                .collect(Collectors.toList());

        pagedPurchasesResponse.setPage(purchasePage.getNumber() + 1);
        pagedPurchasesResponse.setPageSize(purchasePage.getSize());
        pagedPurchasesResponse.setTotalRecords(purchasePage.getTotalElements());
        pagedPurchasesResponse.setTotalPages(purchasePage.getTotalPages());

        if (pagedPurchasesResponse.getTotalPages() == 0) {
            pagedPurchasesResponse.setTotalPages(1);
        }
        pagedPurchasesResponse.setPurchases(purchases);

        return pagedPurchasesResponse;
    }

    @Override
    @Transactional
    public PurchaseDetailDTO getPurchaseById(Long id) {
        Purchase foundPurchase = purchaseRepository.getPurchaseById(id);
        List<PurchaseDetail> foundPurchaseDetails = purchaseDetailRepository.getAllPurchaseDetails(id);
        return purchaseMapper.purchaseDetailToDTO(foundPurchase, foundPurchaseDetails);
    }
}