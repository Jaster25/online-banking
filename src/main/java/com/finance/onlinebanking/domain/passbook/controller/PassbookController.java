package com.finance.onlinebanking.domain.passbook.controller;

import com.finance.onlinebanking.domain.passbook.dto.*;
import com.finance.onlinebanking.domain.passbook.service.PassbookService;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionsHistoryResponseDto;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.global.common.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "통장 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passbooks")
public class PassbookController {

    private final PassbookService passbookService;


    @Operation(summary = "입출금 통장 개설", description = "로그인 사용자가 입출금 통장을 개설한다.")
    @ApiResponse(responseCode = "201", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbooksResponseDto.class)))
    @PostMapping("/deposit-withdraw/banks/{bankId}/products/{productId}")
    public ResponseEntity<PassbookResponseDto> createDepositWithdrawPassbookApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                                                @Parameter(description = "은행 ID") @PathVariable("bankId") Long bankId,
                                                                                @Parameter(description = "상품 ID") @PathVariable("productId") Long productId,
                                                                                @Valid @RequestBody DepositWithdrawPassbookRequestDto depositWithdrawPassbookRequestDto) {
        PassbookResponseDto passbookResponseDto = passbookService.createDepositWithdrawPassbook(user, bankId, productId, depositWithdrawPassbookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
    }

    @Operation(summary = "예금 통장 개설", description = "로그인 사용자가 예금 통장을 개설한다.")
    @ApiResponse(responseCode = "201", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbooksResponseDto.class)))
    @PostMapping("/fixed-deposit/banks/{bankId}/products/{productId}")
    public ResponseEntity<PassbookResponseDto> createFixedDepositPassbookApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                                             @Parameter(description = "은행 ID") @PathVariable("bankId") Long bankId,
                                                                             @Parameter(description = "상품 ID") @PathVariable("productId") Long productId,
                                                                             @Valid @RequestBody FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto) {
        PassbookResponseDto passbookResponseDto = passbookService.createFixedDepositPassbook(user, bankId, productId, fixedDepositPassbookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
    }

    @Operation(summary = "정기 적금 통장 개설", description = "로그인 사용자가 정기 적금 통장을 개설한다.")
    @ApiResponse(responseCode = "201", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbooksResponseDto.class)))
    @PostMapping("/regular-installment/banks/{bankId}/products/{productId}")
    public ResponseEntity<PassbookResponseDto> createRegularInstallmentPassbookApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                                                   @Parameter(description = "은행 ID") @PathVariable("bankId") Long bankId,
                                                                                   @Parameter(description = "상품 ID") @PathVariable("productId") Long productId,
                                                                                   @Valid @RequestBody RegularInstallmentPassbookRequestDto regularInstallmentPassbookRequestDto) {
        PassbookResponseDto passbookResponseDto = passbookService.createRegularInstallmentPassbook(user, bankId, productId, regularInstallmentPassbookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
    }

    @Operation(summary = "자유 적금 통장 개설", description = "로그인 사용자가 자유 적금 통장을 개설한다.")
    @ApiResponse(responseCode = "201", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbooksResponseDto.class)))
    @PostMapping("/free-installment/banks/{bankId}/products/{productId}")
    public ResponseEntity<PassbookResponseDto> createFreeInstallmentPassbookApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                                                @Parameter(description = "은행 ID") @PathVariable("bankId") Long bankId,
                                                                                @Parameter(description = "상품 ID") @PathVariable("productId") Long productId,
                                                                                @Valid @RequestBody FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto) {
        PassbookResponseDto passbookResponseDto = passbookService.createFreeInstallmentPassbook(user, bankId, productId, freeInstallmentPassbookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookResponseDto);
    }

    @Operation(summary = "통장 해지", description = "로그인 사용자가 통장을 해지한다.")
    @ApiResponse(responseCode = "204", description = "successful operation")
    @DeleteMapping("/{passbookId}")
    public ResponseEntity<Void> deletePassbookApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                  @Parameter(description = "통장 ID") @PathVariable("passbookId") Long passbookId) {
        passbookService.deletePassbook(user, passbookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(summary = "통장 잔액 조회", description = "로그인 사용자가 잔액을 조회한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbookBalanceResponseDto.class)))
    @GetMapping("/{passbookId}/balance")
    public ResponseEntity<PassbookBalanceResponseDto> getBalanceApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                                    @Parameter(description = "통장 ID") @PathVariable("passbookId") Long passbookId) {
        PassbookBalanceResponseDto passbookBalanceResponseDto = passbookService.getBalance(user, passbookId);
        return ResponseEntity.status(HttpStatus.OK).body(passbookBalanceResponseDto);
    }

    @Operation(summary = "통장 상세 조회", description = "로그인 사용자가 통장을 상세 조회한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbookResponseDto.class)))
    @GetMapping("/{passbookId}")
    public ResponseEntity<PassbookResponseDto> getPassbookApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                              @Parameter(description = "통장 ID") @PathVariable("passbookId") Long passbookId) {
        PassbookResponseDto passbookResponseDto = passbookService.getPassbook(user, passbookId);
        return ResponseEntity.status(HttpStatus.OK).body(passbookResponseDto);
    }

    @Operation(summary = "통장 비밀번호 변경", description = "로그인 사용자가 통장 비밀번호를 변경한다.")
    @ApiResponse(responseCode = "204", description = "successful operation")
    @PutMapping("/{passbookId}/password")
    public ResponseEntity<Void> updatePassbookPasswordApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                          @Parameter(description = "통장 ID") @PathVariable("passbookId") Long passbookId,
                                                          @Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        passbookService.updatePassword(user, passbookId, passwordRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(summary = "이체 한도 변경", description = "로그인 사용자가 입출금 통장의 이체한도를 변경한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = TransferLimitResponseDto.class)))
    @PutMapping("/{passbookId}/transfer-limit")
    public ResponseEntity<TransferLimitResponseDto> updateTransferLimitApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                                           @Parameter(description = "통장 ID") @PathVariable("passbookId") Long passbookId,
                                                                           @Valid @RequestBody TransferLimitRequestDto transferLimitRequestDto) {
        TransferLimitResponseDto transferLimitResponseDto = passbookService.updateTransferLimit(user, passbookId, transferLimitRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(transferLimitResponseDto);
    }

    @Operation(summary = "이체", description = "통장 간의 이체를 진행한다.")
    @ApiResponse(responseCode = "201", description = "successful operation",
            content = @Content(schema = @Schema(implementation = TransferResponseDto.class)))
    @PostMapping("/{passbookId}/transfer/{depositPassbookId}")
    public ResponseEntity<TransferResponseDto> createTransferApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                                 @Parameter(description = "통장 ID") @PathVariable("passbookId") Long passbookId,
                                                                 @Parameter(description = "입금 통장 ID") @PathVariable("depositPassbookId") Long depositPassbookId,
                                                                 @Valid @RequestBody TransferRequestDto transferRequestDto) {
        TransferResponseDto transferResponseDto = passbookService.createTransfer(user, passbookId, depositPassbookId, transferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferResponseDto);
    }

    @Operation(summary = "통장 거래내역 목록 조회", description = "로그인 사용자가 통장의 거래내역 목록을 조회한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = TransactionsHistoryResponseDto.class)))
    @GetMapping("/{passbookId}/transactions")
    public ResponseEntity<TransactionsHistoryResponseDto> getPassbookTransactionsApi(@Parameter(hidden = true) @CurrentUser UserEntity user,
                                                                                     @Parameter(description = "통장 ID") @PathVariable("passbookId") Long passbookId) {
        TransactionsHistoryResponseDto transactionsHistoryResponseDto = passbookService.getPassbookTransactions(user, passbookId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionsHistoryResponseDto);
    }
}
