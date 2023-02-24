package com.finance.onlinebanking.domain.transactionhistory.repository;

import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TransactionHistoryRepositoryTest {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @DisplayName("삭제되지 않은 거래내역 조회")
    @Nested
    class FindByIdAndIsDeletedFalseTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            Optional<TransactionHistoryEntity> optionalTransactionHistoryEntity = transactionHistoryRepository.findByIdAndIsDeletedFalse(20L);

            // then
            assertTrue(optionalTransactionHistoryEntity.isPresent());
            assertEquals(2000L, optionalTransactionHistoryEntity.get().getAmount());
            assertEquals(500L, optionalTransactionHistoryEntity.get().getCommission());
            assertEquals("user1 입출금 통장1 -> user1 입출금 통장2", optionalTransactionHistoryEntity.get().getMemo());
        }

        @DisplayName("실패 - 삭제된 거래내역 조회")
        @Test
        void failure_deletedTransactionHistory() throws Exception {
            // given
            // when
            Optional<TransactionHistoryEntity> optionalTransactionHistoryEntity = transactionHistoryRepository.findByIdAndIsDeletedFalse(24L);

            // then
            assertTrue(optionalTransactionHistoryEntity.isEmpty());
        }
    }
}