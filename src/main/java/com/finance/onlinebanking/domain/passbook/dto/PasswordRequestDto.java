package com.finance.onlinebanking.domain.passbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordRequestDto {

    private String password;

    @Builder
    public PasswordRequestDto(String password) {
        this.password = password;
    }
}
