package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PassbookBalanceResponseDto {

    private Long id;

    private String accountNumber;

    private Long balance;


    @Builder
    public PassbookBalanceResponseDto(Long id, String accountNumber, Long balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
