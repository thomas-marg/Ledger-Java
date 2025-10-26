package com.example.ledger.controller;

import com.example.ledger.dto.BalanceResponse;
import com.example.ledger.dto.TransactionRequest;
import com.example.ledger.dto.TransactionResponse;
import com.example.ledger.model.Transaction;
import com.example.ledger.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> recordTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = ledgerService.recordTransaction(
                request.getAmount(),
                request.getType()
        );
        TransactionResponse response = toResponse(transaction);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        BigDecimal currentBalance = ledgerService.getCurrentBalance();
        return ResponseEntity.ok(new BalanceResponse(currentBalance));
    }

    @GetMapping("/transactionHistory")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory() {
        List<Transaction> history = ledgerService.getTransactionHistory();
        List<TransactionResponse> responseList = history.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    /**
     * Converts a Transaction entity to its DTO response format.
     */
    private TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getAmount(),
                transaction.getType(),
                transaction.getTimestamp(),
                transaction.getId());
    }
}
