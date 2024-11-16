package com.inventorysystem.Backend;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EsewaPaymentRequest {
    private Double amount;  // Make sure this matches your purchase amount field type
    private Long purchaseId;


}
