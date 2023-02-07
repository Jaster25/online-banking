package com.finance.onlinebanking.domain.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankRequestDto {

    @Schema(description = "은행 이름", defaultValue = "A은행")
    @NotBlank(message = "NOT_NULL_BANK_NAME")
    private String name;

    @Schema(description = "은행 코드", defaultValue = "001")
    @NotBlank(message = "NOT_NULL_BANK_CODE")
    @Pattern(regexp = "([0-9]{3})", message = "INVALID_BANK_CODE")
    private String code;

    @Schema(description = "은행 지점", defaultValue = "군자역")
    @NotBlank(message = "NOT_NULL_BANK_BRANCH")
    private String branch;


    @Builder
    public BankRequestDto(String name, String code, String branch) {
        this.name = name;
        this.code = code;
        this.branch = branch;
    }
}
