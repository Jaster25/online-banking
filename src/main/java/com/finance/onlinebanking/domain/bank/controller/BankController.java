package com.finance.onlinebanking.domain.bank.controller;

import com.finance.onlinebanking.domain.bank.dto.BankRequestDto;
import com.finance.onlinebanking.domain.bank.dto.BankResponseDto;
import com.finance.onlinebanking.domain.bank.service.BankService;
import com.finance.onlinebanking.domain.product.dto.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banks")
public class BankController {

    private final BankService bankService;

    @PostMapping
    public ResponseEntity<BankResponseDto> createBankApi(@Valid @RequestBody BankRequestDto bankRequestDto) {
        BankResponseDto bankResponseDto = bankService.createBank(bankRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankResponseDto);
    }

    @GetMapping("/{bankId}")
    public ResponseEntity<BankResponseDto> getBankApi(@PathVariable Long bankId) {
        BankResponseDto bankResponseDto = bankService.getBank(bankId);
        return ResponseEntity.status(HttpStatus.OK).body(bankResponseDto);
    }

    @GetMapping("/{bankId}/products")
    public ResponseEntity<ProductsResponseDto> getProductsApi(@PathVariable Long bankId) {
        ProductsResponseDto productsResponseDto = bankService.getProducts(bankId);
        return ResponseEntity.status(HttpStatus.OK).body(productsResponseDto);
    }
}
