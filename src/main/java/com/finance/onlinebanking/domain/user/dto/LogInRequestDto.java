package com.finance.onlinebanking.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogInRequestDto {

    @Schema(description = "아이디", defaultValue = "testId1")
    @NotBlank(message = "NOT_NULL_USER_ID")
    private String id;

    @Schema(description = "비밀번호", defaultValue = "1234Aa!")
    @NotBlank(message = "NOT_NULL_USER_PASSWORD")
    private String password;


    @Builder
    public LogInRequestDto(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
