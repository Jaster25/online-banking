package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransferLimitRequestDto {

    private Long transferLimit;


    @Builder
    public TransferLimitRequestDto(Long transferLimit) {
        this.transferLimit = transferLimit;
    }
}
