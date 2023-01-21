package com.finance.onlinebanking.domain.bank.service;

import com.finance.onlinebanking.domain.bank.dto.BankRequestDto;
import com.finance.onlinebanking.domain.bank.dto.BankResponseDto;
import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

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
                .build();
    }

    public BankResponseDto getBank(Long bankId) {
        BankEntity bankEntity = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 은행 ID입니다."));

        return BankResponseDto.builder()
                .id(bankEntity.getId())
                .name(bankEntity.getName())
                .code(bankEntity.getCode())
                .branch(bankEntity.getBranch())
                .build();
    }
}
