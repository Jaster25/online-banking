package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransferResponseDto {

    private final String withdrawAccountNumber;

    private final String depositAccountNumber;

    private final Long amount;

    private final String memo;

    private final Long commission;

    private final LocalDateTime createdAt;


    @Builder
    public TransferResponseDto(String withdrawAccountNumber, String depositAccountNumber, Long amount, String memo, Long commission, LocalDateTime createdAt) {
        this.withdrawAccountNumber = withdrawAccountNumber;
        this.depositAccountNumber = depositAccountNumber;
        this.amount = amount;
        this.memo = memo;
        this.commission = commission;
        this.createdAt = createdAt;
    }
}
