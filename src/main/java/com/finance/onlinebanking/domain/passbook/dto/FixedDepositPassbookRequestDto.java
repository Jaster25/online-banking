package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FixedDepositPassbookRequestDto {

    private String password;

    private Long balance;

    private BigDecimal interestRate;

    private LocalDateTime expiredAt;


    public FixedDepositPassbookRequestDto(String password, Long balance, BigDecimal interestRate, LocalDateTime expiredAt) {
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
        this.expiredAt = expiredAt;
    }
}
