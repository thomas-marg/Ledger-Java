package com.example.ledger.controller;

import com.example.ledger.dto.TransactionRequest;
import com.example.ledger.model.Transaction;
import com.example.ledger.service.LedgerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> recordTransaction(@RequestBody TransactionRequest request) {
        Transaction transaction = ledgerService.recordTransaction(
                request.getAmount(),
                request.getType()
        );
        return ResponseEntity.ok(transaction);
    }
}
