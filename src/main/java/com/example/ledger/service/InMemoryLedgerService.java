package com.example.ledger.service;

import com.example.ledger.model.Balance;
import com.example.ledger.model.Transaction;
import com.example.ledger.model.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryLedgerService implements LedgerService{

    private final List<Transaction> transactions = new ArrayList<>();
    private final Balance balance = new Balance();

    @Override
    public synchronized Transaction recordTransaction(BigDecimal amount, TransactionType type) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        if (type == TransactionType.WITHDRAWAL && amount.compareTo(balance.getAmount()) > 0) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal.");
        }

        Transaction transaction = new Transaction(amount, type);

        transactions.add(transaction);

        if (type == TransactionType.DEPOSIT) {
            balance.deposit(amount);
        } else if (type == TransactionType.WITHDRAWAL) {
            balance.withdraw(amount);
        }

        return transaction;
    }

    @Override
    public synchronized BigDecimal getCurrentBalance() {
        return balance.getAmount();
    }

    @Override
    public synchronized List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactions);
    }
}
