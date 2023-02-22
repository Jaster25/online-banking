package com.finance.onlinebanking.domain.user.service;

import com.finance.onlinebanking.domain.user.dto.UserRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserResponseDto;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
import com.finance.onlinebanking.global.exception.custom.DuplicatedValueException;
import com.finance.onlinebanking.global.exception.custom.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원 가입")
    @Nested
    class CreateUserTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .id("user1")
                    .password("1234Aa@@")
                    .name("김회원")
                    .build();
            given(userRepository.findByUsername(anyString()))
                    .willReturn(Optional.empty());

            // when
            UserResponseDto userResponseDto = userService.createUser(userRequestDto);

            // then
            assertEquals(userRequestDto.getId(), userResponseDto.getId());
            verify(userRepository, times(1)).save(any(UserEntity.class));
        }

        @DisplayName("실패 - 이미 존재하는 사용자 ID")
        @Test
        void failure_duplicatedUserId() throws Exception {
            // given
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .id("user1")
                    .password("1234Aa@@")
                    .name("김회원")
                    .build();
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .password("1234Bb!!")
                    .name("김기존")
                    .build();
            given(userRepository.findByUsername(anyString()))
                    .willReturn(Optional.of(userEntity));

            // when
            // then
            assertThrows(DuplicatedValueException.class,
                    () -> userService.createUser(userRequestDto));
        }
    }

    @DisplayName("사용자 비밀번호 변경")
    @Nested
    class UpdateUserPasswordTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            String password = "1234Aa@@";
            String newPassword = "Bb1234@@";
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .password(password)
                    .name("김회원")
                    .build();
            given(passwordEncoder.encode(anyString()))
                    .willReturn(newPassword);

            // when
            userService.updatePassword(userEntity, newPassword);

            // then
            assertEquals(newPassword, userEntity.getPassword());
            verify(userRepository, times(1)).save(any(UserEntity.class));
        }
    }

    @DisplayName("회원 탈퇴")
    @Nested
    class DeleteUserTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .password("1234Aa@@")
                    .name("김회원")
                    .isDeleted(false)
                    .build();

            // when
            userService.deleteUser(userEntity);

            // then
            assertTrue(userEntity.isDeleted());
            verify(userRepository, times(1)).save(any(UserEntity.class));
        }

        @DisplayName("실패 - 이미 탈퇴한 사용자")
        @Test
        void failure_alreadyDeletedUser() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .password("1234Aa@@")
                    .name("김회원")
                    .isDeleted(true)
                    .build();

            // when
            // then
            assertThrows(InvalidValueException.class,
                    () -> userService.deleteUser(userEntity));
        }
    }
}