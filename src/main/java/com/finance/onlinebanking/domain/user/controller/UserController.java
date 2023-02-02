package com.finance.onlinebanking.domain.user.controller;

import com.finance.onlinebanking.domain.passbook.dto.PassbooksResponseDto;
import com.finance.onlinebanking.domain.passbook.service.PassbookService;
import com.finance.onlinebanking.domain.user.dto.PasswordRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserResponseDto;
import com.finance.onlinebanking.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PassbookService passbookService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUserApi(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @GetMapping("/passbooks")
    public ResponseEntity<PassbooksResponseDto> getUserPassbooksApi() {
        PassbooksResponseDto passbooksResponseDto = passbookService.getPassbooks(1L);
        return ResponseEntity.status(HttpStatus.OK).body(passbooksResponseDto);
    }

    @PutMapping("/password")
    public ResponseEntity<UserResponseDto> updatePasswordApi(@RequestBody PasswordRequestDto passwordRequestDto) {
        userService.updatePassword(passwordRequestDto.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserApi() {
        userService.deleteUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
