package com.example.ledger.dto;

import com.example.ledger.model.TransactionType;

import java.math.BigDecimal;

public class TransactionRequest {
    private TransactionType type;
    private BigDecimal amount;

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
