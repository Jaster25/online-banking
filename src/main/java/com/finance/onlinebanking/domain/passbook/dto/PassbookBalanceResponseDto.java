package com.finance.onlinebanking.domain.passbook.dto;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
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


    public static PassbookBalanceResponseDto of(PassbookEntity passbookEntity) {
        return PassbookBalanceResponseDto.builder()
                .id(passbookEntity.getId())
                .accountNumber(passbookEntity.getAccountNumber())
                .balance(passbookEntity.getBalance())
                .build();
    }
}