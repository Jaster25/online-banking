package com.finance.onlinebanking.domain.passbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class PasswordRequestDto {

    @Schema(description = "비밀번호", defaultValue = "123")
    @NotBlank(message = "NOT_NULL_PASSBOOK_PASSWORD")
    @Pattern(regexp = "([0-9]+)", message = "INVALID_PASSBOOK_PASSWORD")
    private String password;

    @Builder
    public PasswordRequestDto(String password) {
        this.password = password;
    }
}
