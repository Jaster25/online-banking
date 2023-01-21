package com.finance.onlinebanking.domain.bank.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankRequestDto {

    private String name;

    private String code;

    private String branch;


    @Builder
    public BankRequestDto(String name, String code, String branch) {
        this.name = name;
        this.code = code;
        this.branch = branch;
    }
}
