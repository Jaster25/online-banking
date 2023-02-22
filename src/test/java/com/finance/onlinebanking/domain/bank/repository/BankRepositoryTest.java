package com.finance.onlinebanking.domain.bank.repository;


import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepository;

    @DisplayName("삭제되지 않은 은행 조회")
    @Nested
    class FindByIdAndIsDeletedFalseTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            Optional<BankEntity> optionalBankEntity = bankRepository.findByIdAndIsDeletedFalse(1L);

            // then
            assertTrue(optionalBankEntity.isPresent());
            assertEquals("A은행", optionalBankEntity.get().getName());
            assertEquals("001", optionalBankEntity.get().getCode());
            assertEquals("오목교", optionalBankEntity.get().getBranch());
        }

        @DisplayName("성공 - 삭제된 은행 ID 조회")
        @Test
        void failure_deletedBankId() throws Exception {
            // given
            // when
            Optional<BankEntity> optionalBankEntity = bankRepository.findByIdAndIsDeletedFalse(3L);

            // then
            assertTrue(optionalBankEntity.isEmpty());
        }
    }
}