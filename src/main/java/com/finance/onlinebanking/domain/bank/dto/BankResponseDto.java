package com.finance.onlinebanking.domain.bank.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BankResponseDto {

    private final Long id;

    private final String name;

    private final String code;

    private final String branch;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;


    @Builder
    public BankResponseDto(Long id, String name, String code, String branch, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.branch = branch;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
