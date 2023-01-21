package com.finance.onlinebanking.domain.bank.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BankResponseDto {

    private final Long id;

    private final String name;

    private final String code;

    private final String branch;


    @Builder
    public BankResponseDto(Long id, String name, String code, String branch) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.branch = branch;
    }
}
