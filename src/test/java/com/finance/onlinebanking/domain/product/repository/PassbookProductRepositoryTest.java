package com.finance.onlinebanking.domain.product.repository;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PassbookProductRepositoryTest {

    @Autowired
    private PassbookProductRepository passbookProductRepository;

    @Autowired
    private BankRepository bankRepository;

    @DisplayName("삭제되지 않은 특정 은행의 상품 목록 조회")
    @Nested
    class FindAllByBankAndIsDeletedFalseTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            BankEntity bankEntity = bankRepository.findByIdAndIsDeletedFalse(1L).get();

            // when
            List<PassbookProductEntity> passbookProductEntityList = passbookProductRepository.findAllByBankAndIsDeletedFalse(bankEntity);

            // then
            assertEquals(2, passbookProductEntityList.size());
        }
    }

    @DisplayName("삭제되지 않은 상품 조회")
    @Nested
    class FindByIdAndIsDeletedFalseTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            Optional<PassbookProductEntity> optionalPassbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(1L);

            // then
            assertTrue(optionalPassbookProductEntity.isPresent());
            assertEquals("입출금 수수료 면제", optionalPassbookProductEntity.get().getBenefit());
            assertEquals("사회초년생", optionalPassbookProductEntity.get().getConditions());
        }

        @DisplayName("실패 - 삭제된 상품 ID")
        @Test
        void failure_deletedProductId() throws Exception {
            // given
            // when
            Optional<PassbookProductEntity> optionalPassbookProductEntity = passbookProductRepository.findByIdAndIsDeletedFalse(3L);

            // then
            assertTrue(optionalPassbookProductEntity.isEmpty());
        }
    }
}