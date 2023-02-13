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
import com.finance.onlinebanking.global.exception.custom.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    public PassbookResponseDto createDepositWithdrawPassbook(UserEntity userEntity, Long bankId, Long productId, DepositWithdrawPassbookRequestDto depositWithdrawPassbookRequestDto) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

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
        userRepository.save(userEntity);

        return PassbookResponseDto.of(depositWithdrawEntity);
    }

    @Transactional
    public PassbookResponseDto createFixedDepositPassbook(  UserEntity userEntity, Long bankId, Long productId, FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto  ) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

        FixedDepositEntity fixedDepositEntity = FixedDepositEntity.builder()
                .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
                .password(fixedDepositPassbookRequestDto.getPassword())
                .balance(fixedDepositPassbookRequestDto.getBalance())
                .interestRate(fixedDepositPassbookRequestDto.getInterestRate())
                .expiredAt(LocalDateTime.now().plusDays(passbookProductEntity.getTerm()))
                .dtype("FD")
                .build();

        fixedDepositEntity.setBank(bankEntity);
        fixedDepositEntity.setPassbookProduct(passbookProductEntity);
        fixedDepositEntity.setUser(userEntity);

        fixedDepositRepository.save(fixedDepositEntity);
        userRepository.save(userEntity);

        return PassbookResponseDto.of(fixedDepositEntity);
    }

    @Transactional
    public PassbookResponseDto createRegularInstallmentPassbook(UserEntity userEntity, Long bankId, Long productId, RegularInstallmentPassbookRequestDto regularInstallmentPassbookRequestDto) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

        RegularInstallmentEntity regularInstallmentEntity = RegularInstallmentEntity.builder()
                .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
                .password(regularInstallmentPassbookRequestDto.getPassword())
                .balance(regularInstallmentPassbookRequestDto.getBalance())
                .interestRate(regularInstallmentPassbookRequestDto.getInterestRate())
                .depositDate(regularInstallmentPassbookRequestDto.getDepositDate())
                .amount(regularInstallmentPassbookRequestDto.getAmount())
                .expiredAt(LocalDateTime.now().plusDays(passbookProductEntity.getTerm()))
                .dtype("RI")
                .build();

        regularInstallmentEntity.setBank(bankEntity);
        regularInstallmentEntity.setPassbookProduct(passbookProductEntity);
        regularInstallmentEntity.setUser(userEntity);

        regularInstallmentRepository.save(regularInstallmentEntity);
        userRepository.save(userEntity);

        return PassbookResponseDto.of(regularInstallmentEntity);
    }

    @Transactional
    public PassbookResponseDto createFreeInstallmentPassbook(UserEntity userEntity, Long bankId, Long productId, FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto) {
        BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(bankId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_BANK));

        PassbookProductEntity passbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK_PRODUCT));

        FreeInstallmentEntity freeInstallmentEntity = FreeInstallmentEntity.builder()
                .accountNumber(createAccountNumber(bankId, bankEntity.getCode()))
                .password(freeInstallmentPassbookRequestDto.getPassword())
                .balance(freeInstallmentPassbookRequestDto.getBalance())
                .interestRate(freeInstallmentPassbookRequestDto.getInterestRate())
                .expiredAt(LocalDateTime.now().plusDays(passbookProductEntity.getTerm()))
                .dtype("FI")
                .build();

        freeInstallmentEntity.setBank(bankEntity);
        freeInstallmentEntity.setPassbookProduct(passbookProductEntity);
        freeInstallmentEntity.setUser(userEntity);

        freeInstallmentRepository.save(freeInstallmentEntity);
        userRepository.save(userEntity);

        return PassbookResponseDto.of(freeInstallmentEntity);
    }

    @Transactional
    public void deletePassbook(UserEntity userEntity, Long passbookId) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!userEntity.equals(passbookEntity.getUser()) && !userEntity.isAdmin()) {
            throw new UnAuthorizedException(ErrorCode.NONEXISTENT_AUTHORIZATION);
        }

        passbookEntity.delete();
    }

    public PassbookBalanceResponseDto getBalance(UserEntity userEntity, Long passbookId) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(()->new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!userEntity.equals(passbookEntity.getUser()) && !userEntity.isAdmin()) {
            throw new UnAuthorizedException(ErrorCode.NONEXISTENT_AUTHORIZATION);
        }

        return PassbookBalanceResponseDto.of(passbookEntity);
    }

    public PassbookResponseDto getPassbook(UserEntity userEntity, Long passbookId) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!userEntity.equals(passbookEntity.getUser()) && !userEntity.isAdmin()) {
            throw new UnAuthorizedException(ErrorCode.NONEXISTENT_AUTHORIZATION);
        }

        return PassbookResponseDto.of(passbookEntity);
    }

    public PassbooksResponseDto getPassbooks(UserEntity user) {
        List<PassbookEntity> passbooks = passbookRepository.findAllByUserAndIsDeletedFalse(user);

        return PassbooksResponseDto.builder()
                .passbooks(passbooks.stream()
                        .map(PassbookResponseDto::of)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void updatePassword(UserEntity userEntity, Long passbookId, PasswordRequestDto passwordRequestDto) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!userEntity.equals(passbookEntity.getUser()) && !userEntity.isAdmin()) {
            throw new UnAuthorizedException(ErrorCode.NONEXISTENT_AUTHORIZATION);
        }

        passbookEntity.updatePassword(passwordRequestDto.getPassword());
    }

    @Transactional
    public TransferLimitResponseDto updateTransferLimit(UserEntity userEntity, Long passbookId, TransferLimitRequestDto transferLimitRequestDto) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!passbookEntity.isDepositWithdrawPassbook()) {
            throw new InvalidValueException(ErrorCode.INVALID_PASSBOOK_TYPE);
        }

        if (!userEntity.equals(passbookEntity.getUser()) && !userEntity.isAdmin()) {
            throw new UnAuthorizedException(ErrorCode.NONEXISTENT_AUTHORIZATION);
        }

        DepositWithdrawEntity depositWithdrawEntity = (DepositWithdrawEntity) passbookEntity;
        depositWithdrawEntity.updateTransferLimit(transferLimitRequestDto.getTransferLimit());

        return TransferLimitResponseDto.of(depositWithdrawEntity);
    }

    @Transactional
    public TransferResponseDto createTransfer(UserEntity userEntity, Long passbookId, Long depositPassbookId, TransferRequestDto transferRequestDto) {
        PassbookEntity withdrawPassbook = passbookRepository.findByIdAndIsDeletedFalseForUpdate(passbookId)
                .orElseThrow(()-> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));
        PassbookEntity depositPassbook = passbookRepository.findByIdAndIsDeletedFalseForUpdate(depositPassbookId)
                .orElseThrow(()-> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!userEntity.equals(withdrawPassbook.getUser()) && !userEntity.isAdmin()) {
            throw new UnAuthorizedException(ErrorCode.NONEXISTENT_AUTHORIZATION);
        }

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

    public TransactionsHistoryResponseDto getPassbookTransactions(UserEntity userEntity, Long passbookId) {
        PassbookEntity passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(passbookId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_PASSBOOK));

        if (!userEntity.equals(passbookEntity.getUser()) && !userEntity.isAdmin()) {
            throw new UnAuthorizedException(ErrorCode.NONEXISTENT_AUTHORIZATION);
        }

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

