package com.example.ledger.service;

import com.example.ledger.TestUtils;
import com.example.ledger.dto.TransactionRequest;
import com.example.ledger.exception.InsufficientBalanceException;
import com.example.ledger.model.Transaction;
import com.example.ledger.model.TransactionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryLedgerServiceTest {

    private LedgerService ledgerService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ledgerService = new InMemoryLedgerService();
        objectMapper = new ObjectMapper();
    }

    private TransactionRequest loadRequest(String fileName) throws Exception {
        String json = TestUtils.loadJson(fileName);
        return objectMapper.readValue(json, TransactionRequest.class);
    }

    @Test
    void depositIncreasesBalance() throws Exception {
        TransactionRequest request = loadRequest("valid_deposit.json");
        ledgerService.recordTransaction(request.getAmount(), request.getType());
        assertEquals(request.getAmount(), ledgerService.getCurrentBalance());
    }

    @Test
    void withdrawalDecreasesBalance() throws Exception {
        TransactionRequest deposit = loadRequest("valid_deposit.json");
        TransactionRequest withdrawal = loadRequest("valid_withdrawal.json");

        ledgerService.recordTransaction(deposit.getAmount(), deposit.getType());
        ledgerService.recordTransaction(withdrawal.getAmount(), withdrawal.getType());

        BigDecimal expectedBalance = deposit.getAmount().subtract(withdrawal.getAmount());
        assertEquals(expectedBalance, ledgerService.getCurrentBalance());
    }

    @Test
    void transactionHistoryIsStoredCorrectly() throws Exception {
        TransactionRequest request = loadRequest("valid_deposit.json");
        ledgerService.recordTransaction(request.getAmount(), request.getType());

        List<Transaction> history = ledgerService.getTransactionHistory();
        assertEquals(1, history.size());
    }

    @Test
    void withdrawalGreaterThanBalanceThrowsException() throws Exception {
        TransactionRequest withdrawal = loadRequest("valid_withdrawal.json");
        ledgerService.recordTransaction(BigDecimal.valueOf(10), TransactionType.DEPOSIT);

        Exception exception = assertThrows(InsufficientBalanceException.class, () ->
                ledgerService.recordTransaction(withdrawal.getAmount(), withdrawal.getType())
        );

        assertEquals("Insufficient balance for withdrawal.", exception.getMessage());
    }

    @Test
    void negativeAmountThrowsException() throws Exception {
        TransactionRequest request = loadRequest("missing_type.json"); // simulate wrong data
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ledgerService.recordTransaction(BigDecimal.valueOf(-50), TransactionType.DEPOSIT)
        );
        assertEquals("Amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void zeroAmountThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ledgerService.recordTransaction(BigDecimal.ZERO, TransactionType.WITHDRAWAL)
        );
        assertEquals("Amount must be greater than zero.", exception.getMessage());
    }
}