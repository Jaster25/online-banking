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
    public PassbookProductResponseDto createProduct(Long bankId, PassbookProductRequestDto productRequestDto) {
        BankEntity bankEntity = bankRepository.findById(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                .name(productRequestDto.getName())
                .startedAt(productRequestDto.getStartedAt())
                .endedAt(productRequestDto.getEndedAt())
                .interestRate(productRequestDto.getInterestRate())
                .benefit(productRequestDto.getBenefit())
                .content(productRequestDto.getContent())
                .conditions(productRequestDto.getConditions())
                .term(productRequestDto.getTerm())
                .amount(productRequestDto.getAmount())
                .build();

        passbookProductEntity.setBank(bankEntity);

        passbookProductRepository.save(passbookProductEntity);

        return PassbookProductResponseDto.builder()
                .id(passbookProductEntity.getId())
                .bankId(passbookProductEntity.getBank().getId())
                .name(passbookProductEntity.getName())
                .startedAt(passbookProductEntity.getStartedAt())
                .endedAt(passbookProductEntity.getEndedAt())
                .interestRate(passbookProductEntity.getInterestRate())
                .benefit(passbookProductEntity.getBenefit())
                .content(passbookProductEntity.getContent())
                .conditions(passbookProductEntity.getConditions())
                .term(passbookProductEntity.getTerm())
                .amount(passbookProductEntity.getAmount())
                .expiredAt(passbookProductEntity.getExpiredAt())
                .createdAt(passbookProductEntity.getCreatedAt())
                .updatedAt(passbookProductEntity.getUpdatedAt())
                .build();
    }

    public PassbookProductResponseDto getProduct(Long productId) {

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findById(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PRODUCT));

        return PassbookProductResponseDto.builder()
                .id(passbookProductEntity.getId())
                .bankId(passbookProductEntity.getBank().getId())
                .name(passbookProductEntity.getName())
                .startedAt(passbookProductEntity.getStartedAt())
                .endedAt(passbookProductEntity.getEndedAt())
                .interestRate(passbookProductEntity.getInterestRate())
                .benefit(passbookProductEntity.getBenefit())
                .content(passbookProductEntity.getContent())
                .conditions(passbookProductEntity.getConditions())
                .term(passbookProductEntity.getTerm())
                .amount(passbookProductEntity.getAmount())
                .expiredAt(passbookProductEntity.getExpiredAt())
                .createdAt(passbookProductEntity.getCreatedAt())
                .updatedAt(passbookProductEntity.getUpdatedAt())
                .build();
    }
}
