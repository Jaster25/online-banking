package com.finance.onlinebanking.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LogInResponseDto {

    @Schema(description = "Access Token")
    private final String accessToken;


    @Builder
    public LogInResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
