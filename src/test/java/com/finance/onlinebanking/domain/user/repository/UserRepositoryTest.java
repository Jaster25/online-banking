package com.finance.onlinebanking.domain.user.repository;

import com.finance.onlinebanking.domain.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("사용자 아이디로 조회")
    @Nested
    class FindByUsernameTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            Optional<UserEntity> optionalUserEntity = userRepository.findByUsername("user1");

            // then
            assertTrue(optionalUserEntity.isPresent());
            assertEquals("회원1", optionalUserEntity.get().getName());
        }
        
        @DisplayName("성공 - 존재하지 않는 사용자 아이디")
        @Test
        void success_nonExistentUserId() throws Exception {
            // given
            // when
            Optional<UserEntity> optionalUserEntity = userRepository.findByUsername("user45");

            // then
            assertTrue(optionalUserEntity.isEmpty());
        }
    }

    @DisplayName("삭제되지 않은 사용자 ID로 조회")
    @Nested
    class FindByIdAndIsDeletedFalseTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            Optional<UserEntity> optionalUserEntity = userRepository.findByIdAndIsDeletedFalse(11L);

            // then
            assertTrue(optionalUserEntity.isPresent());
            assertEquals("회원1", optionalUserEntity.get().getName());
        }

        @DisplayName("성공 - 삭제된 사용자 조회")
        @Test
        void success_deletedUser() throws Exception {
            // given
            // when
            Optional<UserEntity> optionalUserEntity = userRepository.findByIdAndIsDeletedFalse(13L);

            // then
            assertTrue(optionalUserEntity.isEmpty());
        }
    }
}