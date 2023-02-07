package com.finance.onlinebanking.domain.user.controller;

import com.finance.onlinebanking.domain.passbook.dto.PassbooksResponseDto;
import com.finance.onlinebanking.domain.passbook.service.PassbookService;
import com.finance.onlinebanking.domain.user.dto.PasswordRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserResponseDto;
import com.finance.onlinebanking.domain.user.service.UserService;
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

@Tag(name = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final PassbookService passbookService;

    @Operation(summary = "회원 가입", description = "비로그인 사용자가 회원 가입을 한다.")
    @ApiResponse(responseCode = "201", description = "successful operation",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    @PostMapping
    public ResponseEntity<UserResponseDto> createUserApi(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @Operation(summary = "사용자 통장 목록 조회", description = "로그인 사용자의 통장 목록을 조회한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbooksResponseDto.class)))
    @GetMapping("/passbooks")
    public ResponseEntity<PassbooksResponseDto> getUserPassbooksApi() {
        PassbooksResponseDto passbooksResponseDto = passbookService.getPassbooks(1L);
        return ResponseEntity.status(HttpStatus.OK).body(passbooksResponseDto);
    }

    @Operation(summary = "사용자 비밀번호 변경", description = "로그인 사용자의 비밀번호를 변경한다.")
    @ApiResponse(responseCode = "204", description = "successful operation")
    @PutMapping("/password")
    public ResponseEntity<UserResponseDto> updatePasswordApi(@Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        userService.updatePassword(passwordRequestDto.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(summary = "회원 탈퇴", description = "로그인 사용자가 계정을 탈퇴한다.")
    @ApiResponse(responseCode = "204", description = "successful operation")
    @DeleteMapping
    public ResponseEntity<Void> deleteUserApi() {
        userService.deleteUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
