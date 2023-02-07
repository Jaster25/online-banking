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
public class FreeInstallmentPassbookRequestDto {

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

    @Schema(description = "만기일", defaultValue = "2024-12-20T02:17:35")
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
