package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransferRequestDto {

    private Long amount;

    private String memo;

    private Long commission;


    @Builder
    public TransferRequestDto(Long amount, String memo, Long commission) {
        this.amount = amount;
        this.memo = memo;
        this.commission = commission;
    }
}
