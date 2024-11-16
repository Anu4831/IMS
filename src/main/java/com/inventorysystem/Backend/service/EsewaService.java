package com.inventorysystem.Backend.service;

import com.inventorysystem.Backend.dto.purchase.PaymentDTO;
import com.inventorysystem.Backend.dto.purchase.PurchaseDetailDTO;

public interface EsewaService {
//    String initiateEsewaPayment(PurchaseDetailDTO purchaseDetail);
String initiatePayment(PaymentDTO paymentRequest);

    String initiatePayment(PurchaseDetailDTO purchaseDetail);

    String verifyPayment(String pid, String amt, String refId);
}
