package com.inventorysystem.Backend.controller;

import com.inventorysystem.Backend.EsewaPaymentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @PostMapping("/pay-with-esewa")
    public ResponseEntity<?> initiateEsewaPayment(@RequestBody EsewaPaymentRequest paymentRequest) {
        String paymentUrl = "https://uat.esewa.com.np/epay/main";
        String merchantId = "EPAYTEST"; // From your credentials
        String returnUrl = "http://localhost:8080/api/esewa-success"; // Update with your success URL
        String failureUrl = "http://localhost:8080/api/esewa-failure"; // Update with your failure URL

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("amt", String.valueOf(paymentRequest.getAmount()));
        params.add("pdc", "0");
        params.add("psc", "0");
        params.add("txAmt", "0");
        params.add("tAmt", String.valueOf(paymentRequest.getAmount()));
        params.add("pid", "PURCHASE-" + paymentRequest.getPurchaseId());
        params.add("scd", merchantId);
        params.add("su", returnUrl);
        params.add("fu", failureUrl);

        String fullUrl = UriComponentsBuilder.fromHttpUrl(paymentUrl)
                .queryParams(params)
                .toUriString();

        return ResponseEntity.ok(Collections.singletonMap("paymentUrl", fullUrl));
    }

    // Add success and failure endpoints
    @GetMapping("/esewa-success")
    public ResponseEntity<?> handleSuccess() {
        // Process the success response and update purchase status
        return ResponseEntity.ok("Payment successful");
    }

    @GetMapping("/esewa-failure")
    public ResponseEntity<?> handleFailure() {
        // Handle payment failure
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
    }
}
