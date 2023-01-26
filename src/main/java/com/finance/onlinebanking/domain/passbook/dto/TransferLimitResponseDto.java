package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TransferLimitResponseDto {

    private final Long id;

    private final String accountNumber;

    private final Long transferLimit;


    @Builder
    public TransferLimitResponseDto(Long id, String accountNumber, Long transferLimit) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transferLimit = transferLimit;
    }
}
