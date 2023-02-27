package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PassbookRepositoryTest {

    @Autowired
    private PassbookRepository passbookRepository;


    @DisplayName("삭제되지 않은 통장 ID로 목록 조회")
    @Nested
    class FindAllByUserAndIsDeletedFalseTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();

            // when
            List<PassbookEntity> passbookEntityList = passbookRepository.findAllByUserAndIsDeletedFalse(userEntity);

            // then
            assertEquals(3, passbookEntityList.size());
        }

        @DisplayName("성공 - 삭제된 통장 조회")
        @Test
        void success_deletedPassbook() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .id(15L)
                    .build();

            // when
            List<PassbookEntity> passbookEntityList = passbookRepository.findAllByUserAndIsDeletedFalse(userEntity);

            // then
            assertTrue(passbookEntityList.isEmpty());
        }
    }

    @DisplayName("삭제되지 않은 통장 ID로 목록 조회: Lock")
    @Nested
    class FindByIdAndIsDeletedFalseForUpdateTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            Optional<PassbookEntity> passbookEntity = passbookRepository.findByIdAndIsDeletedFalseForUpdate(13L);

            // then
            assertEquals("4-502-40008003", passbookEntity.get().getAccountNumber());
        }

        @DisplayName("성공 - 존재하지 않는 통장 ID")
        @Test
        void success_deletedPassbook() throws Exception {
            // given
            // when
            Optional<PassbookEntity> passbookEntity = passbookRepository.findByIdAndIsDeletedFalseForUpdate(14L);

            // then
            assertTrue(passbookEntity.isEmpty());
        }
    }

    @DisplayName("계좌번호 존재 여부 조회")
    @Nested
    class ExistsByAccountNumberTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            Boolean isExist = passbookRepository.existsByAccountNumber("4-502-40008003");

            // then
            assertTrue(isExist);
        }

        @DisplayName("성공 - 존재하지 않는 계좌번호로 조회")
        @Test
        void success_deletedPassbook() throws Exception {
            // given
            // when
            Boolean isExist = passbookRepository.existsByAccountNumber("10-502-40008003");

            // then
            assertFalse(isExist);
        }
    }

    @DisplayName("해지되지 않은 통장 ID로 조회")
    @Nested
    class FindByIdAndIsDeletedFalseTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            Optional<PassbookEntity> passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(13L);

            // then
            assertEquals("4-502-40008003", passbookEntity.get().getAccountNumber());
        }

        @DisplayName("성공 - 존재하지 않는 통장 ID")
        @Test
        void success_deletedPassbook() throws Exception {
            // given
            // when
            Optional<PassbookEntity> passbookEntity = passbookRepository.findByIdAndIsDeletedFalse(14L);

            // then
            assertTrue(passbookEntity.isEmpty());
        }
    }
}