package com.example.ledger.controller;

import com.example.ledger.dto.BalanceResponse;
import com.example.ledger.dto.TransactionRequest;
import com.example.ledger.model.Transaction;
import com.example.ledger.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> recordTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = ledgerService.recordTransaction(
                request.getAmount(),
                request.getType()
        );
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        BigDecimal currentBalance = ledgerService.getCurrentBalance();
        return ResponseEntity.ok(new BalanceResponse(currentBalance));
    }
}
