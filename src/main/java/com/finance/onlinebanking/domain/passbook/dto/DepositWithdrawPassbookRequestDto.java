package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class DepositWithdrawPassbookRequestDto {

    private String password;

    private Long balance;

    private BigDecimal interestRate;

    private Long transferLimit;


    @Builder
    public DepositWithdrawPassbookRequestDto(String password, Long balance, BigDecimal interestRate, Long transferLimit) {
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
        this.transferLimit = transferLimit;
    }
}
