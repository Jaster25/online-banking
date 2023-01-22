package com.finance.onlinebanking.domain.transactionhistory.repository;

import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Long> {
}
