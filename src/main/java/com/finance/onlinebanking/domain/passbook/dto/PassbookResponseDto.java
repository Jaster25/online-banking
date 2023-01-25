package com.finance.onlinebanking.domain.passbook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.entity.FixedDepositEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.FreeInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.RegularInstallmentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PassbookResponseDto {

    private Long id;

    private String accountNumber;

    private Long balance;

    private BigDecimal interestRate;

    private Long userId;

    private Long bankId;

    private Long passbookProductId;

    private Long transferLimit;

    private Long amount;

    private String dtype;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expiredAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime depositDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;


    @Builder
    public PassbookResponseDto(Long id, String accountNumber, Long balance, BigDecimal interestRate, Long userId, Long bankId, Long passbookProductId, Long transferLimit, Long amount, String dtype, LocalDateTime expiredAt, LocalDateTime depositDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.userId = userId;
        this.bankId = bankId;
        this.passbookProductId = passbookProductId;
        this.transferLimit = transferLimit;
        this.amount = amount;
        this.dtype = dtype;
        this.expiredAt = expiredAt;
        this.depositDate = depositDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PassbookResponseDto depositWithdrawBuilder(DepositWithdrawEntity depositWithdrawEntity) {
        return PassbookResponseDto.builder()
                .id(depositWithdrawEntity.getId())
                .dtype(depositWithdrawEntity.getDtype())
                .accountNumber(depositWithdrawEntity.getAccountNumber())
                .balance(depositWithdrawEntity.getBalance())
                .interestRate(depositWithdrawEntity.getInterestRate())
                .transferLimit(depositWithdrawEntity.getTransferLimit())
                .userId(depositWithdrawEntity.getUser().getId())
                .bankId(depositWithdrawEntity.getBank().getId())
                .passbookProductId(depositWithdrawEntity.getPassbookProduct().getId())
                .createdAt(depositWithdrawEntity.getCreatedAt())
                .updatedAt(depositWithdrawEntity.getUpdatedAt())
                .build();
    }

    public PassbookResponseDto fixedDepositBuilder(FixedDepositEntity fixedDepositEntity) {
        return PassbookResponseDto.builder()
                .id(fixedDepositEntity.getId())
                .dtype(fixedDepositEntity.getDtype())
                .accountNumber(fixedDepositEntity.getAccountNumber())
                .balance(fixedDepositEntity.getBalance())
                .interestRate(fixedDepositEntity.getInterestRate())
                .userId(fixedDepositEntity.getUser().getId())
                .bankId(fixedDepositEntity.getBank().getId())
                .passbookProductId(fixedDepositEntity.getPassbookProduct().getId())
                .expiredAt(fixedDepositEntity.getExpiredAt())
                .createdAt(fixedDepositEntity.getCreatedAt())
                .updatedAt(fixedDepositEntity.getUpdatedAt())
                .build();
    }

    public PassbookResponseDto regularInstallmentBuilder(RegularInstallmentEntity regularInstallmentEntity) {
        return PassbookResponseDto.builder()
                .id(regularInstallmentEntity.getId())
                .dtype(regularInstallmentEntity.getDtype())
                .accountNumber(regularInstallmentEntity.getAccountNumber())
                .balance(regularInstallmentEntity.getBalance())
                .interestRate(regularInstallmentEntity.getInterestRate())
                .userId(regularInstallmentEntity.getUser().getId())
                .bankId(regularInstallmentEntity.getBank().getId())
                .passbookProductId(regularInstallmentEntity.getPassbookProduct().getId())
                .expiredAt(regularInstallmentEntity.getExpiredAt())
                .depositDate(regularInstallmentEntity.getDepositDate())
                .amount(regularInstallmentEntity.getAmount())
                .createdAt(regularInstallmentEntity.getCreatedAt())
                .updatedAt(regularInstallmentEntity.getUpdatedAt())
                .build();
    }

    public PassbookResponseDto freeInstallmentBuilder(FreeInstallmentEntity freeInstallmentEntity) {
        return PassbookResponseDto.builder()
                .id(freeInstallmentEntity.getId())
                .dtype(freeInstallmentEntity.getDtype())
                .accountNumber(freeInstallmentEntity.getAccountNumber())
                .balance(freeInstallmentEntity.getBalance())
                .interestRate(freeInstallmentEntity.getInterestRate())
                .userId(freeInstallmentEntity.getUser().getId())
                .bankId(freeInstallmentEntity.getBank().getId())
                .passbookProductId(freeInstallmentEntity.getPassbookProduct().getId())
                .expiredAt(freeInstallmentEntity.getExpiredAt())
                .createdAt(freeInstallmentEntity.getCreatedAt())
                .updatedAt(freeInstallmentEntity.getUpdatedAt())
                .build();
    }
}