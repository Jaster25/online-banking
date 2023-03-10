package com.finance.onlinebanking.domain.bank.service;

import com.finance.onlinebanking.domain.bank.dto.BankRequestDto;
import com.finance.onlinebanking.domain.bank.dto.BankResponseDto;
import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.product.dto.PassbookProductResponseDto;
import com.finance.onlinebanking.domain.product.dto.ProductsResponseDto;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    private final PassbookProductRepository passbookProductRepository;

    @Transactional
    public BankResponseDto createBank(BankRequestDto bankRequestDto) {
        // TODO: 중복된 은행 코드 확인

        BankEntity bankEntity = BankEntity.builder()
                .name(bankRequestDto.getName())
                .code(bankRequestDto.getCode())
                .branch(bankRequestDto.getBranch())
                .build();

        bankRepository.save(bankEntity);

        return BankResponseDto.builder()
                .id(bankEntity.getId())
                .name(bankEntity.getName())
                .code(bankEntity.getCode())
                .branch(bankEntity.getBranch())
                .createdAt(bankEntity.getCreatedAt())
                .updatedAt(bankEntity.getUpdatedAt())
                .build();
    }

    public BankResponseDto getBank(Long bankId) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        return BankResponseDto.builder()
                .id(bankEntity.getId())
                .name(bankEntity.getName())
                .code(bankEntity.getCode())
                .branch(bankEntity.getBranch())
                .createdAt(bankEntity.getCreatedAt())
                .updatedAt(bankEntity.getUpdatedAt())
                .build();
    }

    public ProductsResponseDto getProducts(Long bankId) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        List<PassbookProductEntity> passbookProducts = passbookProductRepository.findAllByBankAndIsDeletedFalse(bankEntity);

        List<PassbookProductResponseDto> passbookProductResponseDtos = passbookProducts.stream()
                .map(PassbookProductResponseDto::of)
                .collect(Collectors.toList());

        return ProductsResponseDto.builder()
                .passbookProducts(passbookProductResponseDtos)
                .build();
    }
}
