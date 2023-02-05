package com.finance.onlinebanking.domain.transactionhistory.service;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryRequestDto;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import com.finance.onlinebanking.domain.transactionhistory.repository.TransactionHistoryRepository;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.InvalidValueException;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
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
        // TODO: 유효성 검사
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

    public TransactionHistoryResponseDto getTransactionHistory(Long transactionId) {
        TransactionHistoryEntity transactionHistoryEntity = transactionHistoryRepository.findByIdAndIsDeletedFalse(transactionId)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_TRANSACTION));

        // TODO: 본인 인증

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
