package com.finance.onlinebanking.domain.user.service;

import com.finance.onlinebanking.domain.user.dto.UserRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserResponseDto;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
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
            throw new RuntimeException("이미 존재하는 ID입니다.");
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
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        userEntity.updatePassword(password);
    }

    @Transactional
    public void deleteUser() {
        // 스프링 시큐리티 도입 후 변경 예정
        UserEntity userEntity = userRepository.findByUsername("user1")
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        if (userEntity.isDeleted()) {
            throw new RuntimeException("이미 탈퇴한 사용자입니다.");
        }

        userEntity.delete();
    }
}