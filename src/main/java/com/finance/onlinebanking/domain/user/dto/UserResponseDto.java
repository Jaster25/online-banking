package com.finance.onlinebanking.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    @Schema(description = "아이디", defaultValue = "testId1")
    private final String id;


    @Builder
    public UserResponseDto(String id) {
        this.id = id;
    }
}
