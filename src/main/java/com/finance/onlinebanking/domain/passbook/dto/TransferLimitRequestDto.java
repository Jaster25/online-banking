package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor
public class TransferLimitRequestDto {

    @NotNull(message = "NOT_NULL_TRANSFER_LIMIT")
    @PositiveOrZero(message = "INVALID_TRANSFER_LIMIT")
    private Long transferLimit;


    @Builder
    public TransferLimitRequestDto(Long transferLimit) {
        this.transferLimit = transferLimit;
    }
}
