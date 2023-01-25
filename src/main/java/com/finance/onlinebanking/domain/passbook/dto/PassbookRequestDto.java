package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PassbookRequestDto {

    private String password;

    private Long balance;

    private BigDecimal interestRate;

    private Long transferLimit;

    private LocalDateTime expiredAt;

    private LocalDateTime depositDate;

    private Long amount;

    private String passbookType;


    @Builder
    public PassbookRequestDto(String password, Long balance, BigDecimal interestRate, Long transferLimit, LocalDateTime expiredAt, LocalDateTime depositDate, Long amount, String passbookType) {
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
        this.transferLimit = transferLimit;
        this.expiredAt = expiredAt;
        this.depositDate = depositDate;
        this.amount = amount;
        this.passbookType = passbookType;
    }
}
