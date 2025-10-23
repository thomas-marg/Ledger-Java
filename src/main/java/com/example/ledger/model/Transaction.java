package com.example.ledger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final String id;
    private final BigDecimal amount;
    private final TransactionType type;
    private final LocalDateTime timestamp;

    public Transaction(BigDecimal amount, TransactionType type) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
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
}
