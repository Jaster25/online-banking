package com.finance.onlinebanking.domain.transactionhistory.dto;

import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransactionHistoryResponseDto {

    @Schema(description = "거래내역 아이디")
    private final Long id;

    @Schema(description = "출금 계좌 번호")
    private final String withdrawAccountNumber;

    @Schema(description = "입금 계좌 번호")
    private final String depositAccountNumber;

    @Schema(description = "금액")
    private final Long amount;

    @Schema(description = "메모")
    private final String memo;

    @Schema(description = "수수료")
    private final Long commission;

    @Schema(description = "생성일")
    private final LocalDateTime createdAt;

    @Schema(description = "수정일")
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

    public static TransactionHistoryResponseDto of(TransactionHistoryEntity transactionHistoryEntity) {
        return TransactionHistoryResponseDto.builder()
                .id(transactionHistoryEntity.getId())
                .withdrawAccountNumber(transactionHistoryEntity.getWithdrawAccountNumber())
                .depositAccountNumber(transactionHistoryEntity.getDepositAccountNumber())
                .amount(transactionHistoryEntity.getAmount())
                .memo(transactionHistoryEntity.getMemo())
                .commission(transactionHistoryEntity.getCommission())
                .createdAt(transactionHistoryEntity.getCreatedAt())
                .updatedAt(transactionHistoryEntity.getUpdatedAt())
                .build();
    }
}
