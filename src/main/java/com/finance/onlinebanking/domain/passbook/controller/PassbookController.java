package com.finance.onlinebanking.domain.passbook.controller;

import com.finance.onlinebanking.domain.passbook.dto.*;
import com.finance.onlinebanking.domain.passbook.service.PassbookService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
    }

    @DeleteMapping("/{passbookId}")
    public ResponseEntity<Void> deletePassbookApi(@PathVariable("passbookId") Long passbookId) {
        passbookService.deletePassbook(passbookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{passbookId}/balance")
    public ResponseEntity<PassbookBalanceResponseDto> getBalanceApi(@PathVariable("passbookId") Long passbookId) {
        PassbookBalanceResponseDto passbookBalanceResponseDto = passbookService.getBalance(passbookId);
        return ResponseEntity.status(HttpStatus.OK).body(passbookBalanceResponseDto);
    }

    @GetMapping("/{passbookId}")
    public ResponseEntity<PassbookResponseDto> getPassbookApi(@PathVariable("passbookId") Long passbookId) {
        PassbookResponseDto passbookResponseDto = passbookService.getPassbook(passbookId);
        return ResponseEntity.status(HttpStatus.OK).body(passbookResponseDto);
    }

    @PutMapping("/{passbookId}/password")
    public ResponseEntity<Void> updatePassbookPasswordApi(@PathVariable("passbookId") Long passbookId, @RequestBody PasswordRequestDto passwordRequestDto) {
        passbookService.updatePassword(passbookId, passwordRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping("/{passbookId}/transfer-limit")
    public ResponseEntity<TransferLimitResponseDto> updateTransferLimitApi(@PathVariable("passbookId") Long passbookId, @RequestBody TransferLimitRequestDto transferLimitRequestDto) {
        TransferLimitResponseDto transferLimitResponseDto = passbookService.updateTransferLimit(passbookId, transferLimitRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(transferLimitResponseDto);
    }

    @PostMapping("/{passbookId}/transfer/{depositPassbookId}")
    public ResponseEntity<TransferResponseDto> createTransferApi(@PathVariable("passbookId") Long passbookId,
                                                                 @PathVariable("depositPassbookId") Long depositPassbookId,
                                                                 @RequestBody TransferRequestDto transferRequestDto) {
        TransferResponseDto transferResponseDto = passbookService.createTransfer(passbookId, depositPassbookId, transferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferResponseDto);
    }

    @GetMapping("/{passbookId}/transactions")
    public ResponseEntity<TransactionsResponseDto> getPassbookTransactionsApi(@PathVariable("passbookId") Long passbookId) {
        TransactionsResponseDto transactionsResponseDto = passbookService.getPassbookTransactions(passbookId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionsResponseDto);
    }
}
