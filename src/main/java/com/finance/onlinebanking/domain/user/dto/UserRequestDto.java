package com.finance.onlinebanking.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    @Schema(description = "아이디", defaultValue = "testId1")
    @NotBlank(message = "NOT_NULL_USER_ID")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$", message = "INVALID_USER_ID")
    private String id;

    @Schema(description = "비밀번호", defaultValue = "1234Aa!")
    @NotBlank(message = "NOT_NULL_USER_PASSWORD")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,20}", message = "INVALID_USER_PASSWORD")
    private String password;

    @Schema(description = "사용자 이름", defaultValue = "김테스트")
    @NotBlank(message = "NOT_NULL_USER_NAME")
    private String name;


    @Builder
    public UserRequestDto(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
