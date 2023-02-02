package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PassbooksResponseDto {

    private final List<PassbookResponseDto> passbooks;

    @Builder
    public PassbooksResponseDto(List<PassbookResponseDto> passbooks) {
        this.passbooks = passbooks;
    }
}
