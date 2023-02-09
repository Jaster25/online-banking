package com.finance.onlinebanking.domain.passbook.dto;

import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TransferLimitResponseDto {

    @Schema(description = "통장 ID")
    private final Long id;

    @Schema(description = "계좌 번호")
    private final String accountNumber;

    @Schema(description = "이체 한도")
    private final Long transferLimit;


    @Builder
    public TransferLimitResponseDto(Long id, String accountNumber, Long transferLimit) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transferLimit = transferLimit;
    }


    public static TransferLimitResponseDto of(DepositWithdrawEntity depositWithdrawEntity) {
        return TransferLimitResponseDto.builder()
                .id(depositWithdrawEntity.getId())
                .accountNumber(depositWithdrawEntity.getAccountNumber())
                .transferLimit(depositWithdrawEntity.getTransferLimit())
                .build();
    }
}
