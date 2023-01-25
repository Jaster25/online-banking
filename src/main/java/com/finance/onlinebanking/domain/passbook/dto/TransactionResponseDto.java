package com.finance.onlinebanking.domain.passbook.dto;

import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionResponseDto {

    private final Long id;

    private final String withdrawAccountNumber;

    private final String depositAccountNumber;

    private final Long amount;

    private final String memo;

    private final Long commission;

    private final Long withdrawPassbookId;

    private final Long depositPassbookId;


    @Builder
    public TransactionResponseDto(Long id, String withdrawAccountNumber, String depositAccountNumber, Long amount, String memo, Long commission, Long withdrawPassbookId, Long depositPassbookId) {
        this.id = id;
        this.withdrawAccountNumber = withdrawAccountNumber;
        this.depositAccountNumber = depositAccountNumber;
        this.amount = amount;
        this.memo = memo;
        this.commission = commission;
        this.withdrawPassbookId = withdrawPassbookId;
        this.depositPassbookId = depositPassbookId;
    }

    public static TransactionResponseDto getDto(TransactionHistoryEntity transactionHistoryEntity) {
        return TransactionResponseDto.builder()
                .id(transactionHistoryEntity.getId())
                .withdrawAccountNumber(transactionHistoryEntity.getWithdrawAccountNumber())
                .depositAccountNumber(transactionHistoryEntity.getDepositAccountNumber())
                .amount(transactionHistoryEntity.getAmount())
                .memo(transactionHistoryEntity.getMemo())
                .commission(transactionHistoryEntity.getCommission())
                .withdrawPassbookId(transactionHistoryEntity.getWithdrawPassbook().getId())
                .depositPassbookId(transactionHistoryEntity.getDepositPassbook().getId())
                .build();
    }
}
