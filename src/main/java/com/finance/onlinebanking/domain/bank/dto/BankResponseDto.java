package com.finance.onlinebanking.domain.bank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BankResponseDto {

    @Schema(description = "은행 ID")
    private final Long id;

    @Schema(description = "은행 이름")
    private final String name;

    @Schema(description = "은행 코드")
    private final String code;

    @Schema(description = "은행 지점")
    private final String branch;

    @Schema(description = "생성일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    @Schema(description = "수정일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
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
