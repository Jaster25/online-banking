package com.finance.onlinebanking.domain.passbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class TransferRequestDto {

    @Schema(description = "금액", defaultValue = "500")
    @NotNull(message = "NOT_NULL_TRANSFER_AMOUNT")
    @Positive(message = "INVALID_TRANSFER_AMOUNT")
    private Long amount;

    @Schema(description = "메모", defaultValue = "테스트 송금")
    @NotBlank(message = "NOT_NULL_TRANSFER_MEMO")
    private String memo;

    @Schema(description = "수수료", defaultValue = "50")
    @NotNull(message = "NOT_NULL_TRANSFER_COMMISSION")
    @Positive(message = "INVALID_TRANSFER_COMMISSION")
    private Long commission;


    @Builder
    public TransferRequestDto(Long amount, String memo, Long commission) {
        this.amount = amount;
        this.memo = memo;
        this.commission = commission;
    }
}
