package com.finance.onlinebanking.domain.passbook.dto;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PassbookBalanceResponseDto {

    @Schema(description = "통장 ID")
    private final Long id;

    @Schema(description = "계좌 번호")
    private final String accountNumber;

    @Schema(description = "잔액")
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