package com.example.ledger.service;

import com.example.ledger.model.Transaction;
import com.example.ledger.model.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface LedgerService {
    Transaction recordTransaction(BigDecimal amount, TransactionType type);
    BigDecimal getCurrentBalance();
    List<Transaction> getTransactionHistory();
}
