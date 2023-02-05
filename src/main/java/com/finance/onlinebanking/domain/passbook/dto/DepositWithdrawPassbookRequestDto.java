package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;


@Getter
@NoArgsConstructor
public class DepositWithdrawPassbookRequestDto {

    @NotBlank(message = "NOT_NULL_PASSBOOK_PASSWORD")
    @Pattern(regexp = "([0-9]+)", message = "INVALID_PASSBOOK_PASSWORD")
    private String password;

    @NotNull(message = "NOT_NULL_PASSBOOK_BALANCE")
    @PositiveOrZero(message = "INVALID_PASSBOOK_BALANCE")
    private Long balance;

    @NotNull(message = "NOT_NULL_PASSBOOK_INTEREST_RATE")
    @Positive(message = "INVALID_PASSBOOK_INTEREST_RATE")
    private BigDecimal interestRate;

    @NotNull(message = "NOT_NULL_TRANSFER_LIMIT")
    @PositiveOrZero(message = "INVALID_TRANSFER_LIMIT")
    private Long transferLimit;


    @Builder
    public DepositWithdrawPassbookRequestDto(String password, Long balance, BigDecimal interestRate, Long transferLimit) {
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
        this.transferLimit = transferLimit;
    }
}
