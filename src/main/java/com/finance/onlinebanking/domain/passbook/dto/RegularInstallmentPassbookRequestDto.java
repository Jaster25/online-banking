package com.finance.onlinebanking.domain.passbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RegularInstallmentPassbookRequestDto {

    @Schema(description = "비밀번호", defaultValue = "1234")
    @NotBlank(message = "NOT_NULL_PASSBOOK_PASSWORD")
    @Pattern(regexp = "([0-9]+)", message = "INVALID_PASSBOOK_PASSWORD")
    private String password;

    @Schema(description = "잔액", defaultValue = "10000")
    @NotNull(message = "NOT_NULL_PASSBOOK_BALANCE")
    @PositiveOrZero(message = "INVALID_PASSBOOK_BALANCE")
    private Long balance;

    @Schema(description = "금리", defaultValue = "1.5")
    @NotNull(message = "NOT_NULL_PASSBOOK_INTEREST_RATE")
    @Positive(message = "INVALID_PASSBOOK_INTEREST_RATE")
    private BigDecimal interestRate;
    @Schema(description = "입금일", defaultValue = "2024-12-20T02:17:35")
    @NotNull(message = "NOT_NULL_PASSBOOK_DEPOSIT_DATE")
    @Future(message = "INVALID_PASSBOOK_DEPOSIT_DATE")
    private LocalDateTime depositDate;

    @Schema(description = "금액", defaultValue = "0")
    @NotNull(message = "NOT_NULL_PASSBOOK_AMOUNT")
    @Positive(message = "INVALID_PASSBOOK_AMOUNT")
    private Long amount;

    @Schema(description = "만기일", defaultValue = "2024-12-20T02:17:35")
    @NotNull(message = "NOT_NULL_PASSBOOK_EXPIRED_AT")
    @Future(message = "INVALID_PASSBOOK_EXPIRED_AT")
    private LocalDateTime expiredAt;


    @Builder
    public RegularInstallmentPassbookRequestDto(String password, Long balance, BigDecimal interestRate, LocalDateTime depositDate, Long amount, LocalDateTime expiredAt) {
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
        this.depositDate = depositDate;
        this.amount = amount;
        this.expiredAt = expiredAt;
    }
}
