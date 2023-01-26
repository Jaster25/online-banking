package com.finance.onlinebanking.domain.transactionhistory.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TransactionsHistoryResponseDto {

    private final List<TransactionHistoryResponseDto> transactions;


    @Builder
    public TransactionsHistoryResponseDto(List<TransactionHistoryResponseDto> transactions) {
        this.transactions = transactions;
    }
}
