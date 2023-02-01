package com.finance.onlinebanking.domain.passbook.dto;

import com.finance.onlinebanking.domain.passbook.utils.PassbookType;
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

    public boolean isDepositWithdrawPassbook() {
        return passbookType.equals(PassbookType.DW.toString());
    }

    public boolean isFixedDepositPassbook() {
        return passbookType.equals(PassbookType.FD.toString());
    }

    public boolean isRegularInstallmentPassbook() {
        return passbookType.equals(PassbookType.RI.toString());
    }

    public boolean isInstallmentPassbook() {
        return passbookType.equals(PassbookType.I.toString());
    }

    public boolean isFreeInstallmentPassbook() {
        return passbookType.equals(PassbookType.FI.toString());
    }
}
