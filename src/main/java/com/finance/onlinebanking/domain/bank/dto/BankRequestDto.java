package com.finance.onlinebanking.domain.bank.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankRequestDto {

    @NotBlank(message = "NOT_NULL_BANK_NAME")
    private String name;

    @NotBlank(message = "NOT_NULL_BANK_CODE")
    @Pattern(regexp = "([0-9]{3})", message = "INVALID_BANK_CODE")
    private String code;

    @NotBlank(message = "NOT_NULL_BANK_BRANCH")
    private String branch;


    @Builder
    public BankRequestDto(String name, String code, String branch) {
        this.name = name;
        this.code = code;
        this.branch = branch;
    }
}
