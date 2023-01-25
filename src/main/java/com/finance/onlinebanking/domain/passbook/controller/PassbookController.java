package com.finance.onlinebanking.domain.passbook.controller;

import com.finance.onlinebanking.domain.passbook.dto.PassbookBalanceResponseDto;
import com.finance.onlinebanking.domain.passbook.dto.PassbookRequestDto;
import com.finance.onlinebanking.domain.passbook.dto.PassbookResponseDto;
import com.finance.onlinebanking.domain.passbook.dto.PasswordRequestDto;
import com.finance.onlinebanking.domain.passbook.service.PassbookService;
import com.finance.onlinebanking.domain.passbook.utils.PassbookType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passbooks")
public class PassbookController {

    private final PassbookService passbookService;


    @PostMapping("/bank/{bankId}/product/{productId}/user/{userId}")
    public ResponseEntity<PassbookResponseDto> createPassbookApi(@PathVariable("bankId") Long bankId,
                                                                 @PathVariable("productId") Long productId,
                                                                 @PathVariable("userId") Long userId,
                                                                 @RequestBody PassbookRequestDto passbookRequestDto) {
        PassbookResponseDto passbookResponseDto = passbookService.createPassbook(bankId, productId, userId, passbookRequestDto);
        if (passbookResponseDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{passbookId}/balance")
    public ResponseEntity<PassbookBalanceResponseDto> getBalanceApi(@PathVariable("passbookId") Long passbookId) {
        PassbookBalanceResponseDto passbookBalanceResponseDto = passbookService.getBalance(passbookId);
        return ResponseEntity.status(HttpStatus.OK).body(passbookBalanceResponseDto);
    }

    @GetMapping("/{passbookId}")
    public ResponseEntity<PassbookResponseDto> getPassbookApi(@PathVariable("passbookId") Long passbookId) {
        PassbookResponseDto passbookResponseDto = passbookService.getPassbook(passbookId);
        if (passbookResponseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(passbookResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("{passbookId}/password")
    public ResponseEntity<Void> updatePassbookPassword(@PathVariable("passbookId") Long passbookId, @RequestBody PasswordRequestDto passwordRequestDto) {
        passbookService.updatePassword(passbookId, passwordRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
