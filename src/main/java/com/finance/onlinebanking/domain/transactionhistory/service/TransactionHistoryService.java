package com.finance.onlinebanking.domain.transactionhistory.service;

import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import com.finance.onlinebanking.domain.transactionhistory.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryResponseDto getTransactionHistory(Long transactionId) {
        TransactionHistoryEntity transactionHistoryEntity = transactionHistoryRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 거래내역 ID입니다."));

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
