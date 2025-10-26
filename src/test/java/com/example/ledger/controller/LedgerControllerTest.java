package com.example.ledger.controller;


import com.example.ledger.TestUtils;
import com.example.ledger.dto.TransactionRequest;
import com.example.ledger.model.Transaction;
import com.example.ledger.model.TransactionType;
import com.example.ledger.service.InMemoryLedgerService;
import com.example.ledger.service.LedgerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LedgerControllerTest {
    private LedgerController controller;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        LedgerService service = new InMemoryLedgerService();
        controller = new LedgerController(service);
        objectMapper = new ObjectMapper();
    }

    private TransactionRequest loadRequest(String fileName) throws Exception {
        String json = TestUtils.loadJson(fileName);
        return objectMapper.readValue(json, TransactionRequest.class);
    }

    @Test
    void testValidDeposit() throws Exception {
        TransactionRequest request = loadRequest("valid_deposit.json");

        ResponseEntity<Transaction> response = controller.recordTransaction(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request.getAmount(), response.getBody().getAmount());
        assertEquals(request.getType(), response.getBody().getType());
    }

    @Test
    void testValidWithdrawal() throws Exception {
        TransactionRequest deposit = loadRequest("valid_deposit.json");
        TransactionRequest withdrawal = loadRequest("valid_withdrawal.json");

        controller.recordTransaction(deposit);  // Ensure balance exists
        ResponseEntity<Transaction> response = controller.recordTransaction(withdrawal);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(withdrawal.getAmount(), response.getBody().getAmount());
        assertEquals(withdrawal.getType(), response.getBody().getType());
    }
}