package com.finance.onlinebanking.domain.passbook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.entity.FixedDepositEntity;
import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.InstallmentEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.RegularInstallmentEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PassbookResponseDto {

    @Schema(description = "통장 ID")
    private final Long id;

    @Schema(description = "계좌번호")
    private final String accountNumber;

    @Schema(description = "잔액")
    private final Long balance;

    @Schema(description = "금리")
    private final BigDecimal interestRate;

    @Schema(description = "회원 ID")
    private final Long userId;

    @Schema(description = "은행 ID")
    private final Long bankId;

    @Schema(description = "통장 상품 ID")
    private final Long passbookProductId;

    @Schema(description = "이체 한도")
    private final Long transferLimit;

    @Schema(description = "금액")
    private final Long amount;

    @Schema(description = "통장 종류")
    private final String dtype;

    @Schema(description = "만기일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime expiredAt;

    @Schema(description = "입금 날짜")
    private final int depositDate;

    @Schema(description = "생성일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    @Schema(description = "수정일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime updatedAt;


    @Builder
    public PassbookResponseDto(Long id, String accountNumber, Long balance, BigDecimal interestRate, Long userId, Long bankId, Long passbookProductId, Long transferLimit, Long amount, String dtype, LocalDateTime expiredAt, int depositDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
                        : 0)
                .createdAt(passbookEntity.getCreatedAt())
                .updatedAt(passbookEntity.getUpdatedAt())
                .build();
    }
}
