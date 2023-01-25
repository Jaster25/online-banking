package com.finance.onlinebanking.domain.passbook.service;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.passbook.dto.*;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.entity.FixedDepositEntity;
import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.FreeInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.RegularInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.repository.*;
import com.finance.onlinebanking.domain.passbook.utils.AccountNumberCreator;
import com.finance.onlinebanking.domain.passbook.utils.PassbookType;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryRequestDto;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.repository.TransactionHistoryRepository;
import com.finance.onlinebanking.domain.transactionhistory.service.TransactionHistoryService;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final TransactionHistoryService transactionHistoryService;


    @Transactional
    public PassbookResponseDto createPassbook(Long bankId, Long productId, Long userId, PassbookRequestDto passbookRequestDto) {
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

        if (passbookRequestDto.getPassbookType().equals(PassbookType.DW.toString())) {
            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .accountNumber(new AccountNumberCreator().createAccountNumber(bankId, bankEntity.getCode()))
                    .password(passbookRequestDto.getPassword())
                    .balance(passbookRequestDto.getBalance())
                    .interestRate(passbookRequestDto.getInterestRate())
                    .transferLimit(passbookRequestDto.getTransferLimit())
                    .dtype(passbookRequestDto.getPassbookType())
                    .build();

            depositWithdrawEntity.setBank(bankEntity);
            depositWithdrawEntity.setPassbookProduct(passbookProductEntity);
            depositWithdrawEntity.setUser(userEntity);

            depositWithdrawRepository.save(depositWithdrawEntity);

            return PassbookResponseDto.depositWithdrawBuilder(depositWithdrawEntity);
        } else if (passbookRequestDto.getPassbookType().equals(PassbookType.FD.toString())) {
            FixedDepositEntity fixedDepositEntity = FixedDepositEntity.builder()
                    .accountNumber(new AccountNumberCreator().createAccountNumber(bankId, bankEntity.getCode()))
                    .password(passbookRequestDto.getPassword())
                    .balance(passbookRequestDto.getBalance())
                    .interestRate(passbookRequestDto.getInterestRate())
                    .expiredAt(passbookRequestDto.getExpiredAt())
                    .dtype(passbookRequestDto.getPassbookType())
                    .build();

            fixedDepositEntity.setBank(bankEntity);
            fixedDepositEntity.setPassbookProduct(passbookProductEntity);
            fixedDepositEntity.setUser(userEntity);

            fixedDepositRepository.save(fixedDepositEntity);

            return PassbookResponseDto.fixedDepositBuilder(fixedDepositEntity);
        } else if (passbookRequestDto.getPassbookType().equals(PassbookType.FI.toString())) {
            FreeInstallmentEntity freeInstallmentEntity = FreeInstallmentEntity.builder()
                    .accountNumber(new AccountNumberCreator().createAccountNumber(bankId, bankEntity.getCode()))
                    .password(passbookRequestDto.getPassword())
                    .balance(passbookRequestDto.getBalance())
                    .interestRate(passbookRequestDto.getInterestRate())
                    .expiredAt(passbookRequestDto.getExpiredAt())
                    .dtype(passbookRequestDto.getPassbookType())
                    .build();

            freeInstallmentEntity.setBank(bankEntity);
            freeInstallmentEntity.setPassbookProduct(passbookProductEntity);
            freeInstallmentEntity.setUser(userEntity);

            freeInstallmentRepository.save(freeInstallmentEntity);

            return PassbookResponseDto.freeInstallmentBuilder(freeInstallmentEntity);
        } else if (passbookRequestDto.getPassbookType().equals(PassbookType.RI.toString())) {
            RegularInstallmentEntity regularInstallmentEntity = RegularInstallmentEntity.builder()
                    .accountNumber(new AccountNumberCreator().createAccountNumber(bankId, bankEntity.getCode()))
                    .password(passbookRequestDto.getPassword())
                    .balance(passbookRequestDto.getBalance())
                    .interestRate(passbookRequestDto.getInterestRate())
                    .expiredAt(passbookRequestDto.getExpiredAt())
                    .depositDate(passbookRequestDto.getDepositDate())
                    .amount(passbookRequestDto.getAmount())
                    .dtype(passbookRequestDto.getPassbookType())
                    .build();

            regularInstallmentEntity.setBank(bankEntity);
            regularInstallmentEntity.setPassbookProduct(passbookProductEntity);
            regularInstallmentEntity.setUser(userEntity);

            regularInstallmentRepository.save(regularInstallmentEntity);

            return PassbookResponseDto.regularInstallmentBuilder(regularInstallmentEntity);
        }
        return null;
    }

    @Transactional
    public void deletePassbook(Long passbookId) {
        //TODO: 유효성 검증
        PassbookEntity passbookEntity = passbookRepository.findById(passbookId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 ID 입니다."));

        if (passbookEntity.isDeleted()) {
            throw new RuntimeException("이미 삭제된 통장입니다.");
        }

        passbookEntity.delete();
    }

    public PassbookBalanceResponseDto getBalance(Long passbookId) {
        // TODO: 사용자 검증
        PassbookEntity passbookEntity = passbookRepository.findById(passbookId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 ID 입니다."));

        return PassbookBalanceResponseDto.builder()
                .id(passbookEntity.getId())
                .accountNumber(passbookEntity.getAccountNumber())
                .balance(passbookEntity.getBalance())
                .build();
    }

    public PassbookResponseDto getPassbook(Long passbookId) {
        // TODO: 통장 유효성 검증
        PassbookEntity passbookEntity = passbookRepository.findById(passbookId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 ID 입니다."));

        if (passbookEntity.getDtype().equals(PassbookType.DW.toString())) {
            DepositWithdrawEntity depositWithdrawEntity = depositWithdrawRepository.findById(passbookId)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 입출금 통장 ID 입니다."));
            return PassbookResponseDto.depositWithdrawBuilder(depositWithdrawEntity);
        } else if (passbookEntity.getDtype().equals(PassbookType.FD.toString())) {
            FixedDepositEntity fixedDepositEntity = fixedDepositRepository.findById(passbookId)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 예금 통장 ID 입니다."));
            return PassbookResponseDto.fixedDepositBuilder(fixedDepositEntity);
        } else if (passbookEntity.getDtype().equals(PassbookType.FI.toString())) {
            FreeInstallmentEntity freeInstallmentEntity = freeInstallmentRepository.findById(passbookId)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 자유 적금 통장 ID 입니다."));
            return PassbookResponseDto.freeInstallmentBuilder(freeInstallmentEntity);
        } else if (passbookEntity.getDtype().equals(PassbookType.RI.toString())) {
            RegularInstallmentEntity regularInstallmentEntity = regularInstallmentRepository.findById(passbookId)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 정기 적금 통장 ID 입니다."));
            return PassbookResponseDto.regularInstallmentBuilder(regularInstallmentEntity);
        }
        return null;
    }

    @Transactional
    public void updatePassword(Long passbookId, PasswordRequestDto passwordRequestDto) {
        PassbookEntity passbookEntity = passbookRepository.findById(passbookId)
                .orElseThrow(()->new RuntimeException("존재하지 않는 통장 ID 입니다."));

        passbookEntity.updatePassword(passwordRequestDto.getPassword());
    }

    @Transactional
    public TransferLimitResponseDto updateTransferLimit(Long passbookId, TransferLimitRequestDto transferLimitRequestDto) {
        PassbookEntity passbookEntity = passbookRepository.findById(passbookId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 ID 입니다."));

        if (!passbookEntity.getDtype().equals(PassbookType.DW.toString())) {
            return null;
        }

        DepositWithdrawEntity depositWithdrawEntity = depositWithdrawRepository.findById(passbookEntity.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 입출금 통장 ID 입니다."));

        depositWithdrawEntity.updateTransferLimit(transferLimitRequestDto.getTransferLimit());

        return TransferLimitResponseDto.builder()
                .id(depositWithdrawEntity.getId())
                .accountNumber(depositWithdrawEntity.getAccountNumber())
                .transferLimit(depositWithdrawEntity.getTransferLimit())
                .build();
    }

    @Transactional
    public TransferResponseDto createTransfer(Long passbookId, Long depositPassbookId, TransferRequestDto transferRequestDto) {
        PassbookEntity withdrawPassbook = passbookRepository.findById(passbookId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 출금 통장 ID 입니다."));

        PassbookEntity depositPassbook = passbookRepository.findById(depositPassbookId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 입금 통장 ID 입니다."));

        // TODO: 유효성 검사, 출금 통장 잔액 확인, 출금 통장 이체 한도 확인
        withdrawPassbook.transfer(depositPassbook, transferRequestDto.getAmount());
        passbookRepository.save(withdrawPassbook);
        passbookRepository.save(depositPassbook);

        TransactionHistoryRequestDto transactionHistoryRequestDto = TransactionHistoryRequestDto.builder()
                .withdrawAccountNumber(withdrawPassbook.getAccountNumber())
                .depositAccountNumber(depositPassbook.getAccountNumber())
                .amount(transferRequestDto.getAmount())
                .memo(transferRequestDto.getMemo())
                .commission(transferRequestDto.getCommission())
                .build();

        TransactionHistoryResponseDto transactionHistoryResponseDto = transactionHistoryService.createTransactionHistory(transactionHistoryRequestDto, withdrawPassbook, depositPassbook);

        return TransferResponseDto.builder()
                .withdrawAccountNumber(transactionHistoryResponseDto.getWithdrawAccountNumber())
                .depositAccountNumber(transactionHistoryResponseDto.getDepositAccountNumber())
                .amount(transactionHistoryResponseDto.getAmount())
                .memo(transactionHistoryResponseDto.getMemo())
                .commission(transactionHistoryResponseDto.getCommission())
                .createdAt(transactionHistoryResponseDto.getCreatedAt())
                .build();
    }

    public TransactionsResponseDto getPassbookTransactions(Long passbookId) {
        // TODO: 유효성 검사
        PassbookEntity passbookEntity = passbookRepository.findById(passbookId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 통장 ID 입니다."));

        List<TransactionResponseDto> dtoList =
                Stream.concat(passbookEntity.getWithdrawTransactionHistories()
                                        .stream().map(TransactionResponseDto::getDto)
                                        .collect(Collectors.toList()).stream(),
                                passbookEntity.getDepositTransactionHistories()
                                        .stream().map(TransactionResponseDto::getDto)
                                        .collect(Collectors.toList()).stream())
                        .collect(Collectors.toList());

        return TransactionsResponseDto.builder()
                .transactions(dtoList)
                .build();
    }
}

