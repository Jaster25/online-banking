package com.finance.onlinebanking.domain.passbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PassbooksResponseDto {

    @Schema(description = "통장 목록")
    private final List<PassbookResponseDto> passbooks;

    @Builder
    public PassbooksResponseDto(List<PassbookResponseDto> passbooks) {
        this.passbooks = passbooks;
    }
}
