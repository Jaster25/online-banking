package com.finance.onlinebanking.domain.passbook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expiredAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime depositDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;


    @Builder
    public PassbookResponseDto(Long id, String accountNumber, Long balance, BigDecimal interestRate, Long userId, Long bankId, Long passbookProductId, Long transferLimit, Long amount, LocalDateTime expiredAt, LocalDateTime depositDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.userId = userId;
        this.bankId = bankId;
        this.passbookProductId = passbookProductId;
        this.transferLimit = transferLimit;
        this.amount = amount;
        this.expiredAt = expiredAt;
        this.depositDate = depositDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
