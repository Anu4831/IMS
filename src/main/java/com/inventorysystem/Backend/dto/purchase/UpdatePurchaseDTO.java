package com.inventorysystem.Backend.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePurchaseDTO {
    private Long purchaseId;
    private Integer totalValue; // Optional, if you want to update the total value
    private String paymentStatus; // Updated payment status
}
