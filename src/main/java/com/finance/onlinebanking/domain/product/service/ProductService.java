package com.finance.onlinebanking.domain.product.service;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.product.dto.PassbookProductRequestDto;
import com.finance.onlinebanking.domain.product.dto.PassbookProductResponseDto;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final BankRepository bankRepository;

    private final PassbookProductRepository passbookProductRepository;


    @Transactional
    public PassbookProductResponseDto createProduct(Long bankId, PassbookProductRequestDto passbookProductRequestDto) {
        BankEntity bankEntity = bankRepository.findById(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                .name(passbookProductRequestDto.getName())
                .startedAt(passbookProductRequestDto.getStartedAt())
                .endedAt(passbookProductRequestDto.getEndedAt())
                .interestRate(passbookProductRequestDto.getInterestRate())
                .benefit(passbookProductRequestDto.getBenefit())
                .content(passbookProductRequestDto.getContent())
                .conditions(passbookProductRequestDto.getConditions())
                .term(passbookProductRequestDto.getTerm())
                .amount(passbookProductRequestDto.getAmount())
                .build();

        passbookProductEntity.setBank(bankEntity);

        passbookProductRepository.save(passbookProductEntity);

        return PassbookProductResponseDto.of(passbookProductEntity);
    }

    public PassbookProductResponseDto getProduct(Long productId) {
        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PRODUCT));
        return PassbookProductResponseDto.of(passbookProductEntity);
    }
}
