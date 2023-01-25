package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TransactionsResponseDto {

    private final List<TransactionResponseDto> transactions;

    @Builder
    public TransactionsResponseDto(List<TransactionResponseDto> transactions) {
        this.transactions = transactions;
    }
}
