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
}
