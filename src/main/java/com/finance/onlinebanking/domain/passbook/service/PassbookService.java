package com.finance.onlinebanking.domain.passbook.service;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.passbook.dto.FixedDepositResponseDto;
import com.finance.onlinebanking.domain.passbook.dto.PassbookBalanceResponseDto;
import com.finance.onlinebanking.domain.passbook.dto.PassbookRequestDto;
import com.finance.onlinebanking.domain.passbook.dto.PassbookResponseDto;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.entity.FixedDepositEntity;
import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.FreeInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.RegularInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.repository.*;
import com.finance.onlinebanking.domain.passbook.utils.AccountNumberCreator;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PassbookService {

    private final PassbookProductRepository passbookProductRepository;

    private final BankRepository bankRepository;

    private final UserRepository userRepository;

    private final PassbookRepository passbookRepository;

    private final DepositWithdrawRepository depositWithdrawRepository;

    private final FixedDepositRepository fixedDepositRepository;

    private final FreeInstallmentRepository freeInstallmentRepository;

    private final RegularInstallmentRepository regularInstallmentRepository;


    @Transactional
    public PassbookResponseDto createDepositWithdrawPassbook(Long bankId, Long productId, Long userId, PassbookRequestDto passbookRequestDto) {
        /**
         * TODO
         * 1. 통장 타입 유효성 검증
         * 2. 은행별 계좌번호 생성 규칙에 의한 계좌번호 생성 및 중복 검증
          */
        BankEntity bankEntity = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 은행 ID 입니다."));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 상품 ID 입니다."));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저 ID 입니다."));


        DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                .accountNumber(new AccountNumberCreator().createAccountNumber(bankId, bankEntity.getCode()))
                .password(passbookRequestDto.getPassword())
                .balance(passbookRequestDto.getBalance())
                .interestRate(passbookRequestDto.getInterestRate())
                .transferLimit(passbookRequestDto.getTransferLimit())
                .build();

        depositWithdrawEntity.setBank(bankEntity);
        depositWithdrawEntity.setPassbookProduct(passbookProductEntity);
        depositWithdrawEntity.setUser(userEntity);

        depositWithdrawRepository.save(depositWithdrawEntity);

        return PassbookResponseDto.builder()
                .id(depositWithdrawEntity.getId())
                .accountNumber(depositWithdrawEntity.getAccountNumber())
                .balance(depositWithdrawEntity.getBalance())
                .interestRate(depositWithdrawEntity.getInterestRate())
                .userId(depositWithdrawEntity.getUser().getId())
                .bankId(depositWithdrawEntity.getBank().getId())
                .passbookProductId(depositWithdrawEntity.getPassbookProduct().getId())
                .transferLimit(depositWithdrawEntity.getTransferLimit())
                .createdAt(depositWithdrawEntity.getCreatedAt())
                .updatedAt(depositWithdrawEntity.getUpdatedAt())
                .build();
    }

    @Transactional
    public PassbookResponseDto createFixedDepositPassbook(Long bankId, Long productId, Long userId, PassbookRequestDto passbookRequestDto) {
        BankEntity bankEntity = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 은행 ID 입니다."));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 상품 ID 입니다."));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저 ID 입니다."));

        FixedDepositEntity fixedDepositEntity = FixedDepositEntity.builder()
                .accountNumber(new AccountNumberCreator().createAccountNumber(bankId, bankEntity.getCode()))
                .password(passbookRequestDto.getPassword())
                .balance(passbookRequestDto.getBalance())
                .interestRate(passbookRequestDto.getInterestRate())
                .expiredAt(passbookRequestDto.getExpiredAt())
                .build();

        fixedDepositEntity.setBank(bankEntity);
        fixedDepositEntity.setPassbookProduct(passbookProductEntity);
        fixedDepositEntity.setUser(userEntity);

        fixedDepositRepository.save(fixedDepositEntity);

        return PassbookResponseDto.builder()
                .id(fixedDepositEntity.getId())
                .accountNumber(fixedDepositEntity.getAccountNumber())
                .balance(fixedDepositEntity.getBalance())
                .interestRate(fixedDepositEntity.getInterestRate())
                .userId(fixedDepositEntity.getUser().getId())
                .bankId(fixedDepositEntity.getBank().getId())
                .passbookProductId(fixedDepositEntity.getPassbookProduct().getId())
                .expiredAt(fixedDepositEntity.getExpiredAt())
                .createdAt(fixedDepositEntity.getCreatedAt())
                .updatedAt(fixedDepositEntity.getUpdatedAt())
                .build();
    }

    @Transactional
    public PassbookResponseDto createFreeInstallmentPassbook(Long bankId, Long productId, Long userId, PassbookRequestDto passbookRequestDto) {
        BankEntity bankEntity = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 은행 ID 입니다."));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 상품 ID 입니다."));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저 ID 입니다."));

        FreeInstallmentEntity freeInstallmentEntity = FreeInstallmentEntity.builder()
                .accountNumber(new AccountNumberCreator().createAccountNumber(bankId, bankEntity.getCode()))
                .password(passbookRequestDto.getPassword())
                .balance(passbookRequestDto.getBalance())
                .interestRate(passbookRequestDto.getInterestRate())
                .expiredAt(passbookRequestDto.getExpiredAt())
                .build();

        freeInstallmentEntity.setBank(bankEntity);
        freeInstallmentEntity.setPassbookProduct(passbookProductEntity);
        freeInstallmentEntity.setUser(userEntity);

        freeInstallmentRepository.save(freeInstallmentEntity);

        return PassbookResponseDto.builder()
                .id(freeInstallmentEntity.getId())
                .accountNumber(freeInstallmentEntity.getAccountNumber())
                .balance(freeInstallmentEntity.getBalance())
                .interestRate(freeInstallmentEntity.getInterestRate())
                .userId(freeInstallmentEntity.getUser().getId())
                .bankId(freeInstallmentEntity.getBank().getId())
                .passbookProductId(freeInstallmentEntity.getPassbookProduct().getId())
                .expiredAt(freeInstallmentEntity.getExpiredAt())
                .createdAt(freeInstallmentEntity.getCreatedAt())
                .updatedAt(freeInstallmentEntity.getUpdatedAt())
                .build();
    }

    @Transactional
    public PassbookResponseDto createRegularInstallmentPassbook(Long bankId, Long productId, Long userId, PassbookRequestDto passbookRequestDto) {
        BankEntity bankEntity = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 은행 ID 입니다."));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 상품 ID 입니다."));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저 ID 입니다."));

        RegularInstallmentEntity regularInstallmentEntity = RegularInstallmentEntity.builder()
                .accountNumber(new AccountNumberCreator().createAccountNumber(bankId, bankEntity.getCode()))
                .password(passbookRequestDto.getPassword())
                .balance(passbookRequestDto.getBalance())
                .interestRate(passbookRequestDto.getInterestRate())
                .expiredAt(passbookRequestDto.getExpiredAt())
                .depositDate(passbookRequestDto.getDepositDate())
                .amount(passbookRequestDto.getAmount())
                .build();

        regularInstallmentEntity.setBank(bankEntity);
        regularInstallmentEntity.setPassbookProduct(passbookProductEntity);
        regularInstallmentEntity.setUser(userEntity);

        regularInstallmentRepository.save(regularInstallmentEntity);

        return PassbookResponseDto.builder()
                .id(regularInstallmentEntity.getId())
                .accountNumber(regularInstallmentEntity.getAccountNumber())
                .balance(regularInstallmentEntity.getBalance())
                .interestRate(regularInstallmentEntity.getInterestRate())
                .userId(regularInstallmentEntity.getUser().getId())
                .bankId(regularInstallmentEntity.getBank().getId())
                .passbookProductId(regularInstallmentEntity.getPassbookProduct().getId())
                .expiredAt(regularInstallmentEntity.getExpiredAt())
                .depositDate(regularInstallmentEntity.getDepositDate())
                .amount(regularInstallmentEntity.getAmount())
                .createdAt(regularInstallmentEntity.getCreatedAt())
                .updatedAt(regularInstallmentEntity.getUpdatedAt())
                .build();
    }
}

