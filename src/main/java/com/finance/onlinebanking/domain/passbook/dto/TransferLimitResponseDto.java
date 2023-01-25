package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransferLimitResponseDto {

    private Long id;

    private String accountNumber;

    private Long transferLimit;


    @Builder
    public TransferLimitResponseDto(Long id, String accountNumber, Long transferLimit) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transferLimit = transferLimit;
    }
}
