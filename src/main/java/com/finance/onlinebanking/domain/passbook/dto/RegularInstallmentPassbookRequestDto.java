package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RegularInstallmentPassbookRequestDto {

    private String password;

    private Long balance;

    private BigDecimal interestRate;

    private LocalDateTime depositDate;

    private Long amount;


    @Builder
    public RegularInstallmentPassbookRequestDto(String password, Long balance, BigDecimal interestRate, LocalDateTime depositDate, Long amount) {
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
        this.depositDate = depositDate;
        this.amount = amount;
    }
}
