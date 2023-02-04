package com.finance.onlinebanking.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    @Schema(description = "아이디", defaultValue = "testId1")
    private String id;

    @Schema(description = "비밀번호", defaultValue = "1234Aa!")
    private String password;

    @Schema(description = "사용자 이름", defaultValue = "김테스트")
    private String name;


    @Builder
    public UserRequestDto(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
