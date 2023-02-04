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
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryRequestDto;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionsHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.service.TransactionHistoryService;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.DuplicatedValueException;
import com.finance.onlinebanking.global.exception.custom.InvalidValueException;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
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
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findById(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        if (passbookRequestDto.isDepositWithdrawPassbook()) {
            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
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

            return PassbookResponseDto.of(depositWithdrawEntity);
        } else if (passbookRequestDto.isFixedDepositPassbook()) {
            FixedDepositEntity fixedDepositEntity = FixedDepositEntity.builder()
                    .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
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

            return PassbookResponseDto.of(fixedDepositEntity);
        } else if (passbookRequestDto.isFreeInstallmentPassbook()) {
            FreeInstallmentEntity freeInstallmentEntity = FreeInstallmentEntity.builder()
                    .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
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

            return PassbookResponseDto.of(freeInstallmentEntity);
        } else if (passbookRequestDto.isRegularInstallmentPassbook()) {
            RegularInstallmentEntity regularInstallmentEntity = RegularInstallmentEntity.builder()
                    .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
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

            return PassbookResponseDto.of(regularInstallmentEntity);
        }
        return null;
    }

    @Transactional
    public PassbookResponseDto createDepositWithdrawPassbook(Long bankId, Long productId, Long userId, DepositWithdrawPassbookRequestDto depositWithdrawPassbookRequestDto) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
                .password(depositWithdrawPassbookRequestDto.getPassword())
                .balance(depositWithdrawPassbookRequestDto.getBalance())
                .interestRate(depositWithdrawPassbookRequestDto.getInterestRate())
                .transferLimit(depositWithdrawPassbookRequestDto.getTransferLimit())
                .dtype("DW")
                .build();

            depositWithdrawEntity.setBank(bankEntity);
            depositWithdrawEntity.setPassbookProduct(passbookProductEntity);
            depositWithdrawEntity.setUser(userEntity);

            depositWithdrawRepository.save(depositWithdrawEntity);

        return PassbookResponseDto.of(depositWithdrawEntity);
    }

    @Transactional
    public PassbookResponseDto createFixedDepositPassbook(Long bankId, Long productId, Long userId, FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto  ) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        FixedDepositEntity fixedDepositEntity = FixedDepositEntity.builder()
                .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
                .password(fixedDepositPassbookRequestDto.getPassword())
                .balance(fixedDepositPassbookRequestDto.getBalance())
                .interestRate(fixedDepositPassbookRequestDto.getInterestRate())
                .expiredAt(fixedDepositPassbookRequestDto.getExpiredAt())
                .dtype("FD")
                .build();

        fixedDepositEntity.setBank(bankEntity);
        fixedDepositEntity.setPassbookProduct(passbookProductEntity);
        fixedDepositEntity.setUser(userEntity);

        fixedDepositRepository.save(fixedDepositEntity);

        return PassbookResponseDto.of(fixedDepositEntity);
    }

    @Transactional
    public PassbookResponseDto createRegularInstallmentPassbook(Long bankId, Long productId, Long userId, RegularInstallmentPassbookRequestDto regularInstallmentPassbookRequestDto) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        RegularInstallmentEntity regularInstallmentEntity = RegularInstallmentEntity.builder()
                .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
                .password(regularInstallmentPassbookRequestDto.getPassword())
                .balance(regularInstallmentPassbookRequestDto.getBalance())
                .interestRate(regularInstallmentPassbookRequestDto.getInterestRate())
                .depositDate(regularInstallmentPassbookRequestDto.getDepositDate())
                .amount(regularInstallmentPassbookRequestDto.getAmount())
                .expiredAt((regularInstallmentPassbookRequestDto.getExpiredAt()))
                .dtype("RI")
                .build();

        regularInstallmentEntity.setBank(bankEntity);
        regularInstallmentEntity.setPassbookProduct(passbookProductEntity);
        regularInstallmentEntity.setUser(userEntity);

        regularInstallmentRepository.save(regularInstallmentEntity);

        return PassbookResponseDto.of(regularInstallmentEntity);
    }

    @Transactional
    public PassbookResponseDto createFreeInstallmentPassbook(Long bankId, Long productId, Long userId, FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        FreeInstallmentEntity freeInstallmentEntity = FreeInstallmentEntity.builder()
                .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
                .password(freeInstallmentPassbookRequestDto.getPassword())
                .balance(freeInstallmentPassbookRequestDto.getBalance())
                .interestRate(freeInstallmentPassbookRequestDto.getInterestRate())
                .expiredAt(freeInstallmentPassbookRequestDto.getExpiredAt())
                .dtype("FI")
                .build();

        freeInstallmentEntity.setBank(bankEntity);
        freeInstallmentEntity.setPassbookProduct(passbookProductEntity);
        freeInstallmentEntity.setUser(userEntity);

        freeInstallmentRepository.save(freeInstallmentEntity);

        return PassbookResponseDto.of(freeInstallmentEntity);
    }

    @Transactional
    public void deletePassbook(Long passbookId) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        passbookEntity.delete();
    }

    public PassbookBalanceResponseDto getBalance(Long passbookId) {
        // TODO: 사용자 검증
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(()->new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        return PassbookBalanceResponseDto.of(passbookEntity);
    }

    public PassbookResponseDto getPassbook(Long passbookId) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        return PassbookResponseDto.of(passbookEntity);
    }

    public PassbooksResponseDto getPassbooks(Long userId) {
        UserEntity user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        List<PassbookEntity> passbooks = passbookRepository.findAllByUser(user);

        return PassbooksResponseDto.builder()
                .passbooks(passbooks.stream()
                        .map(PassbookResponseDto::of)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void updatePassword(Long passbookId, PasswordRequestDto passwordRequestDto) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        passbookEntity.updatePassword(passwordRequestDto.getPassword());
    }

    @Transactional
    public TransferLimitResponseDto updateTransferLimit(Long passbookId, TransferLimitRequestDto transferLimitRequestDto) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!passbookEntity.isDepositWithdrawPassbook()) {
            throw new InvalidValueException(ErrorCode.INVALID_PASSBOOK_TYPE);
        }

        DepositWithdrawEntity depositWithdrawEntity = (DepositWithdrawEntity) passbookEntity;
        depositWithdrawEntity.updateTransferLimit(transferLimitRequestDto.getTransferLimit());

        return TransferLimitResponseDto.of(depositWithdrawEntity);
    }

    @Transactional
    public TransferResponseDto createTransfer(Long passbookId, Long depositPassbookId, TransferRequestDto transferRequestDto) {
        PassbookEntity withdrawPassbook = passbookRepository.findByIdAndIsDeletedFalseForUpdate(passbookId)
                .orElseThrow(()-> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));
        PassbookEntity depositPassbook = passbookRepository.findByIdAndIsDeletedFalseForUpdate(depositPassbookId)
                .orElseThrow(()-> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!withdrawPassbook.checkBalance(transferRequestDto.getAmount())) {
            throw new InvalidValueException(ErrorCode.LACK_OF_WITHDRAW_PASSBOOK_BALANCE);
        }

        if (withdrawPassbook.isDepositWithdrawPassbook()) {
            if (!withdrawPassbook.checkTransferLimit(transferRequestDto.getAmount())) {
                throw new InvalidValueException(ErrorCode.LACK_OF_WITHDRAW_PASSBOOK_TRANSFER_LIMIT);
            }
        }

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

        return TransferResponseDto.of(transactionHistoryResponseDto);
    }

    public TransactionsHistoryResponseDto getPassbookTransactions(Long passbookId) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        List<TransactionHistoryResponseDto> dtoList =
                Stream.concat(passbookEntity.getWithdrawTransactionHistories()
                                        .stream().map(TransactionHistoryResponseDto::of)
                                        .collect(Collectors.toList()).stream(),
                                passbookEntity.getDepositTransactionHistories()
                                        .stream().map(TransactionHistoryResponseDto::of)
                                        .collect(Collectors.toList()).stream())
                        .collect(Collectors.toList());

        return TransactionsHistoryResponseDto.of(dtoList);
    }


    public String createAccountNumber(Long bankId, String code) {
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            String accountNumber = bankId.toString() + "-" + code + "-" + Integer.toString(random.nextInt());

            if (!isDuplicated(accountNumber)) {
                return accountNumber;
            }
        }
        throw new DuplicatedValueException(ErrorCode.DUPLICATED_PASSBOOK_ACCOUNT_NUMBER);
    }

    public Boolean isDuplicated(String accountNumber) {
        return passbookRepository.existsByAccountNumber(accountNumber);
    }
}

