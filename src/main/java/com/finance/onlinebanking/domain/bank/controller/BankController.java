package com.finance.onlinebanking.domain.bank.controller;

import com.finance.onlinebanking.domain.bank.dto.BankRequestDto;
import com.finance.onlinebanking.domain.bank.dto.BankResponseDto;
import com.finance.onlinebanking.domain.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banks")
public class BankController {

    private final BankService bankService;

    @PostMapping
    public ResponseEntity<BankResponseDto> createBankApi(@RequestBody BankRequestDto bankRequestDto) {
        BankResponseDto bankResponseDto = bankService.createBank(bankRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankResponseDto);
    }
}
