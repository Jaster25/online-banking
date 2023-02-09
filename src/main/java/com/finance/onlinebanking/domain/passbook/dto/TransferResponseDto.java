package com.finance.onlinebanking.domain.passbook.dto;

import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransferResponseDto {

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


    @Builder
    public TransferResponseDto(String withdrawAccountNumber, String depositAccountNumber, Long amount, String memo, Long commission, LocalDateTime createdAt) {
        this.withdrawAccountNumber = withdrawAccountNumber;
        this.depositAccountNumber = depositAccountNumber;
        this.amount = amount;
        this.memo = memo;
        this.commission = commission;
        this.createdAt = createdAt;
    }

    public static TransferResponseDto of(TransactionHistoryResponseDto transactionHistoryResponseDto) {
        return TransferResponseDto.builder()
                .withdrawAccountNumber(transactionHistoryResponseDto.getWithdrawAccountNumber())
                .depositAccountNumber(transactionHistoryResponseDto.getDepositAccountNumber())
                .amount(transactionHistoryResponseDto.getAmount())
                .memo(transactionHistoryResponseDto.getMemo())
                .commission(transactionHistoryResponseDto.getCommission())
                .createdAt(transactionHistoryResponseDto.getCreatedAt())
                .build();
    }
}
