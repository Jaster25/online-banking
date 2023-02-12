package com.finance.onlinebanking.domain.user.controller;

import com.finance.onlinebanking.domain.user.dto.LogInRequestDto;
import com.finance.onlinebanking.domain.user.dto.LogInResponseDto;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.service.AuthService;
import com.finance.onlinebanking.global.common.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "비로그인 사용자가 로그인을 한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = LogInResponseDto.class)))
    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> logInApi(@RequestBody @Valid LogInRequestDto logInRequestDto) {
        LogInResponseDto logInResponseDto = authService.logIn(logInRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(logInResponseDto);
    }

    @Operation(summary = "로그아웃", description = "로그인 사용자가 로그아웃을 한다.")
    @ApiResponse(responseCode = "204", description = "successful operation")
    @GetMapping("/logout")
    public ResponseEntity<Void> logOutApi(@CurrentUser UserEntity user) {
        // TODO
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}