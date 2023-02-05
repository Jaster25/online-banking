package com.finance.onlinebanking.domain.user.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordRequestDto {

    @NotBlank(message = "NOT_NULL_USER_PASSWORD")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,20}", message = "INVALID_USER_PASSWORD")
    private String password;


    @Builder
    public PasswordRequestDto(String password) {
        this.password = password;
    }
}
