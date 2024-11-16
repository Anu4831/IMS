package com.inventorysystem.Backend.dto.purchase;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private String pid; // Payment ID or identifier
  private String amt; // Amount to be paid
    private String scd; // Merchant ID
    private String su;  // Success URL
    private String fu;
   private String txAmt;
    private Long totalAmount;
    private Long purchaseId;

}
