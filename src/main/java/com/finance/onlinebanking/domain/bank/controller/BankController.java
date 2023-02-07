package com.finance.onlinebanking.domain.bank.controller;

import com.finance.onlinebanking.domain.bank.dto.BankRequestDto;
import com.finance.onlinebanking.domain.bank.dto.BankResponseDto;
import com.finance.onlinebanking.domain.bank.service.BankService;
import com.finance.onlinebanking.domain.product.dto.ProductsResponseDto;
import com.finance.onlinebanking.domain.user.dto.UserResponseDto;
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

@Tag(name = "은행 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banks")
public class BankController {

    private final BankService bankService;

    @Operation(summary = "은행 생성", description = "관리자가 은행을 생성한다.")
    @ApiResponse(responseCode = "201", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BankResponseDto.class)))
    @PostMapping
    public ResponseEntity<BankResponseDto> createBankApi(@Valid @RequestBody BankRequestDto bankRequestDto) {
        BankResponseDto bankResponseDto = bankService.createBank(bankRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankResponseDto);
    }

    @Operation(summary = "은행 상세 조회", description = "은행을 상세 조회한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BankResponseDto.class)))
    @GetMapping("/{bankId}")
    public ResponseEntity<BankResponseDto> getBankApi(@Parameter(description = "은행 ID") @PathVariable Long bankId) {
        BankResponseDto bankResponseDto = bankService.getBank(bankId);
        return ResponseEntity.status(HttpStatus.OK).body(bankResponseDto);
    }

    @Operation(summary = "특정 은행 상품 목록 조회", description = "특정 은행의 상품 목록을 조회한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = ProductsResponseDto.class)))
    @GetMapping("/{bankId}/products")
    public ResponseEntity<ProductsResponseDto> getProductsApi(@Parameter(description = "은행 ID") @PathVariable Long bankId) {
        ProductsResponseDto productsResponseDto = bankService.getProducts(bankId);
        return ResponseEntity.status(HttpStatus.OK).body(productsResponseDto);
    }
}
