package com.finance.onlinebanking.domain.transactionhistory.service;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryRequestDto;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import com.finance.onlinebanking.domain.transactionhistory.repository.TransactionHistoryRepository;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import com.finance.onlinebanking.global.exception.custom.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    @Transactional
    public TransactionHistoryResponseDto createTransactionHistory(TransactionHistoryRequestDto transactionHistoryRequestDto, PassbookEntity withdrawPassbook, PassbookEntity depositPassbook) {
        TransactionHistoryEntity transactionHistoryEntity = TransactionHistoryEntity.builder()
                .withdrawAccountNumber(transactionHistoryRequestDto.getWithdrawAccountNumber())
                .depositAccountNumber(transactionHistoryRequestDto.getDepositAccountNumber())
                .amount(transactionHistoryRequestDto.getAmount())
                .memo(transactionHistoryRequestDto.getMemo())
                .commission(transactionHistoryRequestDto.getCommission())
                .build();
        transactionHistoryEntity.setWithdrawPassbook(withdrawPassbook);
        transactionHistoryEntity.setDepositPassbook(depositPassbook);

        transactionHistoryRepository.save(transactionHistoryEntity);

        return TransactionHistoryResponseDto.builder()
                .id(transactionHistoryEntity.getId())
                .withdrawAccountNumber(transactionHistoryEntity.getWithdrawAccountNumber())
                .depositAccountNumber(transactionHistoryEntity.getDepositAccountNumber())
                .amount(transactionHistoryEntity.getAmount())
                .memo(transactionHistoryEntity.getMemo())
                .commission(transactionHistoryEntity.getCommission())
                .createdAt(transactionHistoryEntity.getCreatedAt())
                .updatedAt(transactionHistoryEntity.getUpdatedAt())
                .build();
    }

    public TransactionHistoryResponseDto getTransactionHistory(UserEntity user, Long transactionId) {
        TransactionHistoryEntity transactionHistoryEntity = transactionHistoryRepository.findByIdAndIsDeletedFalse(transactionId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_TRANSACTION));

        UserEntity depositPassbookOwner = transactionHistoryEntity.getDepositPassbook().getUser();
        UserEntity withdrawPassbookOwner = transactionHistoryEntity.getWithdrawPassbook().getUser();

        if (!user.equals(depositPassbookOwner) && !user.equals(withdrawPassbookOwner) && !user.isAdmin()) {
            throw new UnAuthorizedException(ErrorCode.NONEXISTENT_AUTHORIZATION);
        }

        return TransactionHistoryResponseDto.builder()
                .id(transactionHistoryEntity.getId())
                .withdrawAccountNumber(transactionHistoryEntity.getWithdrawAccountNumber())
                .depositAccountNumber(transactionHistoryEntity.getDepositAccountNumber())
                .amount(transactionHistoryEntity.getAmount())
                .memo(transactionHistoryEntity.getMemo())
                .commission(transactionHistoryEntity.getCommission())
                .createdAt(transactionHistoryEntity.getCreatedAt())
                .updatedAt(transactionHistoryEntity.getUpdatedAt())
                .build();
    }
}
