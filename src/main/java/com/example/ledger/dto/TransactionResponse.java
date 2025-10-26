package com.example.ledger.dto;

import com.example.ledger.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO to expose transaction details in a clean response format.
 */
public class TransactionResponse {
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime timestamp;
    private String id;

    public TransactionResponse(BigDecimal amount, TransactionType type, LocalDateTime timestamp, String id) {
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }
}