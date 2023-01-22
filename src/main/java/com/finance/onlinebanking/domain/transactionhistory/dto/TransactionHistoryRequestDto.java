package com.finance.onlinebanking.domain.transactionhistory.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionHistoryRequestDto {

    private String withdrawAccountNumber;

    private String depositAccountNumber;

    private Long amount;

    private String memo;

    private Long commission;


    @Builder
    public TransactionHistoryRequestDto(String withdrawAccountNumber, String depositAccountNumber, Long amount, String memo, Long commission) {
        this.withdrawAccountNumber = withdrawAccountNumber;
        this.depositAccountNumber = depositAccountNumber;
        this.amount = amount;
        this.memo = memo;
        this.commission = commission;
    }
}
