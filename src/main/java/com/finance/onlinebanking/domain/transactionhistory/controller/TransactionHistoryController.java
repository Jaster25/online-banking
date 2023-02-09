package com.finance.onlinebanking.domain.transactionhistory.controller;

import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.service.TransactionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "거래내역 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @Operation(summary = "거래내역 상세 조회", description = "거래내역을 상세 조회한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = TransactionHistoryResponseDto.class)))
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionHistoryResponseDto> getTransactionHistoryApi(@Parameter(description = "거래내역 ID") @PathVariable Long transactionId) {
        TransactionHistoryResponseDto transactionHistoryResponseDto = transactionHistoryService.getTransactionHistory(transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionHistoryResponseDto);
    }
}
