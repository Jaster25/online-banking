package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PassbookBalanceResponseDto {

    private final Long id;

    private final String accountNumber;

    private final Long balance;


    @Builder
    public PassbookBalanceResponseDto(Long id, String accountNumber, Long balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}