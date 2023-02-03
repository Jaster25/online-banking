package com.finance.onlinebanking.domain.user.service;

import com.finance.onlinebanking.domain.user.dto.UserRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserResponseDto;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.DuplicatedValueException;
import com.finance.onlinebanking.global.exception.custom.InvalidValueException;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getId()).orElse(null) != null) {
            throw new DuplicatedValueException(ErrorCode.DUPLICATED_USER_ID);
        }

        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDto.getId())
                .password(userRequestDto.getPassword())
                .name(userRequestDto.getName())
                .build();

        userRepository.save(userEntity);

        return UserResponseDto.builder()
                .id(userEntity.getUsername())
                .build();
    }

    @Transactional
    public void updatePassword(String password) {
        // 스프링 시큐리티 도입 후 변경 예정
        UserEntity userEntity = userRepository.findByUsername("user1")
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        // TODO: 기존 비밀번호와 다른지 확인

        userEntity.updatePassword(password);
    }

    @Transactional
    public void deleteUser() {
        // 스프링 시큐리티 도입 후 변경 예정
        UserEntity userEntity = userRepository.findByUsername("user1")
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        if (userEntity.isDeleted()) {
            throw new InvalidValueException(ErrorCode.ALREADY_DELETED_USER);
        }

        userEntity.delete();
    }
}
