package com.finance.onlinebanking.domain.user.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordRequestDto {

    private String password;


    @Builder
    public PasswordRequestDto(String password) {
        this.password = password;
    }
}
