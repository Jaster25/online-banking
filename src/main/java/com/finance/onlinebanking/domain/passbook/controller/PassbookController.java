package com.finance.onlinebanking.domain.passbook.controller;

import com.finance.onlinebanking.domain.passbook.dto.*;
import com.finance.onlinebanking.domain.passbook.service.PassbookService;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionsHistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passbooks")
public class PassbookController {

    private final PassbookService passbookService;


    @PostMapping("/deposit-withdraw/banks/{bankId}/products/{productId}/users/{userId}")
    public ResponseEntity<PassbookResponseDto> createDepositWithdrawPassbookApi(@PathVariable("bankId") Long bankId,
                                                                                @PathVariable("productId") Long productId,
                                                                                @PathVariable("userId") Long userId,
                                                                                @Valid @RequestBody DepositWithdrawPassbookRequestDto depositWithdrawPassbookRequestDto) {
        PassbookResponseDto passbookResponseDto = passbookService.createDepositWithdrawPassbook(bankId, productId, userId, depositWithdrawPassbookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
    }

    @PostMapping("/fixed-deposit/banks/{bankId}/products/{productId}/users/{userId}")
    public ResponseEntity<PassbookResponseDto> createFixedDepositPassbookApi(@PathVariable("bankId") Long bankId,
                                                                             @PathVariable("productId") Long productId,
                                                                             @PathVariable("userId") Long userId,
                                                                             @Valid @RequestBody FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto) {
        PassbookResponseDto passbookResponseDto = passbookService.createFixedDepositPassbook(bankId, productId, userId, fixedDepositPassbookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
    }

    @PostMapping("/regular-installment/banks/{bankId}/products/{productId}/users/{userId}")
    public ResponseEntity<PassbookResponseDto> createRegularInstallmentPassbookApi(@PathVariable("bankId") Long bankId,
                                                                                   @PathVariable("productId") Long productId,
                                                                                   @PathVariable("userId") Long userId,
                                                                                   @Valid @RequestBody RegularInstallmentPassbookRequestDto regularInstallmentPassbookRequestDto) {

        PassbookResponseDto passbookResponseDto = passbookService.createRegularInstallmentPassbook(bankId, productId, userId, regularInstallmentPassbookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
    }

    @PostMapping("/free-installment/banks/{bankId}/products/{productId}/users/{userId}")
    public ResponseEntity<PassbookResponseDto> createRegularInstallmentPassbookApi(@PathVariable("bankId") Long bankId,
                                                                                   @PathVariable("productId") Long productId,
                                                                                   @PathVariable("userId") Long userId,
                                                                                   @Valid @RequestBody FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto ) {
        PassbookResponseDto passbookResponseDto = passbookService.createFreeInstallmentPassbook(bankId, productId, userId, freeInstallmentPassbookRequestDto);
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
    public ResponseEntity<Void> updatePassbookPasswordApi(@PathVariable("passbookId") Long passbookId, @Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        passbookService.updatePassword(passbookId, passwordRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping("/{passbookId}/transfer-limit")
    public ResponseEntity<TransferLimitResponseDto> updateTransferLimitApi(@PathVariable("passbookId") Long passbookId, @Valid @RequestBody TransferLimitRequestDto transferLimitRequestDto) {
        TransferLimitResponseDto transferLimitResponseDto = passbookService.updateTransferLimit(passbookId, transferLimitRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(transferLimitResponseDto);
    }

    @PostMapping("/{passbookId}/transfer/{depositPassbookId}")
    public ResponseEntity<TransferResponseDto> createTransferApi(@PathVariable("passbookId") Long passbookId,
                                                                 @PathVariable("depositPassbookId") Long depositPassbookId,
                                                                 @Valid @RequestBody TransferRequestDto transferRequestDto) {
        TransferResponseDto transferResponseDto = passbookService.createTransfer(passbookId, depositPassbookId, transferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferResponseDto);
    }

    @GetMapping("/{passbookId}/transactions")
    public ResponseEntity<TransactionsHistoryResponseDto> getPassbookTransactionsApi(@PathVariable("passbookId") Long passbookId) {
        TransactionsHistoryResponseDto transactionsHistoryResponseDto = passbookService.getPassbookTransactions(passbookId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionsHistoryResponseDto);
    }
}
