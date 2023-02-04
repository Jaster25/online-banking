package com.finance.onlinebanking.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordRequestDto {

    @Schema(description = "변경할 비밀번호")
    private String password;


    @Builder
    public PasswordRequestDto(String password) {
        this.password = password;
    }
}
