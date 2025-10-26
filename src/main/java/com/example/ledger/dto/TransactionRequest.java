package com.example.ledger.dto;

import com.example.ledger.model.TransactionType;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

/**
 * Represents a transaction request for deposits or withdrawals.
 */
public class TransactionRequest {
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    @NotNull(message = "Amount is required")
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
