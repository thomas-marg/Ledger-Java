package com.example.ledger.model;

import java.math.BigDecimal;

public class Balance {

    private BigDecimal amount;

    public Balance() {
        this.amount = BigDecimal.ZERO;
    }

    public Balance(BigDecimal initialAmount) {
        this.amount = initialAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void deposit(BigDecimal value) {
        amount = amount.add(value);
    }

    public void withdraw(BigDecimal value) {
        amount = amount.subtract(value);
    }
}
