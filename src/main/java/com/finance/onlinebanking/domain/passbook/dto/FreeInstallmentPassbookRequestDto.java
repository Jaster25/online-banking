package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class FreeInstallmentPassbookRequestDto {

    private String password;

    private Long balance;

    private BigDecimal interestRate;


    @Builder
    public FreeInstallmentPassbookRequestDto(String password, Long balance, BigDecimal interestRate) {
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
    }
}
