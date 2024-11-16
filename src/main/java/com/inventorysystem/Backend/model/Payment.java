package com.inventorysystem.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data // Automatically generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private double amount; // Added to track the payment amount

    @Column(name = "transaction_id")
    private String transactionId; // Added for linking with the purchase or sale

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Added to store the creation timestamp

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Automatically set the createdAt timestamp
    }
}
