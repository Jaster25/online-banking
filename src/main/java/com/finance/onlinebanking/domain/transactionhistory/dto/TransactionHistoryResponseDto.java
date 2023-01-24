package com.finance.onlinebanking.domain.transactionhistory.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransactionHistoryResponseDto {

    private final Long id;

    private final String withdrawAccountNumber;

    private final String depositAccountNumber;

    private final Long amount;

    private final String memo;

    private final Long commission;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;


    @Builder
    public TransactionHistoryResponseDto(Long id, String withdrawAccountNumber, String depositAccountNumber, Long amount, String memo, Long commission, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.withdrawAccountNumber = withdrawAccountNumber;
        this.depositAccountNumber = depositAccountNumber;
        this.amount = amount;
        this.memo = memo;
        this.commission = commission;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
