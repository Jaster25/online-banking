package com.finance.onlinebanking.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    private String id;

    private String password;

    private String name;


    @Builder
    public UserRequestDto(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
