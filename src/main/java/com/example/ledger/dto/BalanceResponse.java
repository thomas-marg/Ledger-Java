package com.example.ledger.dto;

import java.math.BigDecimal;

/**
 * DTO to return the current balance in a clean format.
 */
public class BalanceResponse {
    private BigDecimal balance;

    public BalanceResponse(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}