package com.finance.onlinebanking.domain.transactionhistory.repository;

import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Long> {

    Optional<TransactionHistoryEntity> findByIdAndIsDeletedFalse(Long transactionId);
}
