package com.finance.onlinebanking.domain.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankRequestDto {

    @Schema(description = "은행 이름", defaultValue = "A은행")
    private String name;

    @Schema(description = "은행 코드", defaultValue = "A001")
    private String code;

    @Schema(description = "은행 지점", defaultValue = "군자역")
    private String branch;


    @Builder
    public BankRequestDto(String name, String code, String branch) {
        this.name = name;
        this.code = code;
        this.branch = branch;
    }
}
