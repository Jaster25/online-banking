package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FreeInstallmentPassbookRequestDto {

    @NotBlank(message = "NOT_NULL_PASSBOOK_PASSWORD")
    @Pattern(regexp = "([0-9]+)", message = "INVALID_PASSBOOK_PASSWORD")
    private String password;

    @NotNull(message = "NOT_NULL_PASSBOOK_BALANCE")
    @PositiveOrZero(message = "INVALID_PASSBOOK_BALANCE")
    private Long balance;

    @NotNull(message = "NOT_NULL_PASSBOOK_INTEREST_RATE")
    @Positive(message = "INVALID_PASSBOOK_INTEREST_RATE")
    private BigDecimal interestRate;

    @NotNull(message = "NOT_NULL_PASSBOOK_EXPIRED_AT")
    @Future(message = "INVALID_PASSBOOK_EXPIRED_AT")
    private LocalDateTime expiredAt;

    @Builder
    public FreeInstallmentPassbookRequestDto(String password, Long balance, BigDecimal interestRate, LocalDateTime expiredAt) {
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
        this.expiredAt = expiredAt;
    }
}
