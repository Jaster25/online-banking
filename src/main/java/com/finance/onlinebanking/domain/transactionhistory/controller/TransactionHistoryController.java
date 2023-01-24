package com.finance.onlinebanking.domain.transactionhistory.controller;

import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionHistoryResponseDto> getTransactionHistoryApi(@PathVariable Long transactionId) {
        TransactionHistoryResponseDto transactionHistoryResponseDto = transactionHistoryService.getTransactionHistory(transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionHistoryResponseDto);
    }
}
