package com.finance.onlinebanking.domain.passbook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.entity.FixedDepositEntity;
import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.InstallmentEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.RegularInstallmentEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PassbookResponseDto {

    private final Long id;

    private final String accountNumber;

    private final Long balance;

    private final BigDecimal interestRate;

    private final Long userId;

    private final Long bankId;

    private final Long passbookProductId;

    private final Long transferLimit;

    private final Long amount;

    private final String dtype;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime expiredAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime depositDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime updatedAt;


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


    public static PassbookResponseDto of(PassbookEntity passbookEntity) {
        return PassbookResponseDto.builder()
                .id(passbookEntity.getId())
                .accountNumber(passbookEntity.getAccountNumber())
                .balance(passbookEntity.getBalance())
                .interestRate(passbookEntity.getInterestRate())
                .userId(passbookEntity.getUser().getId())
                .bankId(passbookEntity.getBank().getId())
                .passbookProductId(passbookEntity.getPassbookProduct().getId())
                .transferLimit(passbookEntity.isDepositWithdrawPassbook()
                        ? ((DepositWithdrawEntity) passbookEntity).getTransferLimit()
                        : null)
                .amount(passbookEntity.isRegularInstallmentPassbook()
                        ? ((RegularInstallmentEntity) passbookEntity).getAmount()
                        : null)
                .dtype(passbookEntity.getDtype())
                .expiredAt(!passbookEntity.isDepositWithdrawPassbook()
                        ? passbookEntity.isFixedDepositPassbook()
                            ? ((FixedDepositEntity) passbookEntity).getExpiredAt()
                            : ((InstallmentEntity) passbookEntity).getExpiredAt()
                        : null)
                .depositDate(passbookEntity.isRegularInstallmentPassbook()
                        ? ((RegularInstallmentEntity) passbookEntity).getDepositDate()
                        : null)
                .createdAt(passbookEntity.getCreatedAt())
                .updatedAt(passbookEntity.getUpdatedAt())
                .build();
    }
}
