package com.finance.onlinebanking.domain.product.service;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.product.dto.PassbookProductRequestDto;
import com.finance.onlinebanking.domain.product.dto.PassbookProductResponseDto;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.entity.ProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final BankRepository bankRepository;

    private final PassbookProductRepository passbookProductRepository;

    private final ProductRepository productRepository;


    @Transactional
    public PassbookProductResponseDto createProduct(Long bankId, PassbookProductRequestDto productRequestDto) {
        BankEntity bankEntity = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 은행 ID 입니다."));

        PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                .name(productRequestDto.getName())
                .startedAt(productRequestDto.getStartedAt())
                .endedAt(productRequestDto.getEndedAt())
                .interestRate(productRequestDto.getInterestRate())
                .benefit(productRequestDto.getBenefit())
                .content(productRequestDto.getContent())
                .condition(productRequestDto.getCondition())
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
                .condition(passbookProductEntity.getCondition())
                .term(passbookProductEntity.getTerm())
                .amount(passbookProductEntity.getAmount())
                .expiredAt(passbookProductEntity.getExpiredAt())
                .createdAt(passbookProductEntity.getCreatedAt())
                .updatedAt(passbookProductEntity.getUpdatedAt())
                .build();
    }

    public PassbookProductResponseDto getProduct(Long productId) {

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품 ID 입니다."));

        return PassbookProductResponseDto.builder()
                .id(passbookProductEntity.getId())
                .bankId(passbookProductEntity.getBank().getId())
                .name(passbookProductEntity.getName())
                .startedAt(passbookProductEntity.getStartedAt())
                .endedAt(passbookProductEntity.getEndedAt())
                .interestRate(passbookProductEntity.getInterestRate())
                .benefit(passbookProductEntity.getBenefit())
                .content(passbookProductEntity.getContent())
                .condition(passbookProductEntity.getCondition())
                .term(passbookProductEntity.getTerm())
                .amount(passbookProductEntity.getAmount())
                .expiredAt(passbookProductEntity.getExpiredAt())
                .createdAt(passbookProductEntity.getCreatedAt())
                .updatedAt(passbookProductEntity.getUpdatedAt())
                .build();
    }
}
