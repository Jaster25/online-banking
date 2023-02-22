package com.finance.onlinebanking.domain.user.service;

import com.finance.onlinebanking.domain.user.dto.UserRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserResponseDto;
import com.finance.onlinebanking.domain.user.entity.Role;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
import com.finance.onlinebanking.global.config.security.UserAdapter;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.DuplicatedValueException;
import com.finance.onlinebanking.global.exception.custom.InvalidValueException;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getId()).orElse(null) != null) {
            throw new DuplicatedValueException(ErrorCode.DUPLICATED_USER_ID);
        }

        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDto.getId())
                .password(encodedPassword)
                .name(userRequestDto.getName())
                .build();
        userEntity.addRole(Role.USER);

        userRepository.save(userEntity);

        return UserResponseDto.builder()
                .id(userEntity.getUsername())
                .build();
    }

    @Transactional
    public void updatePassword(UserEntity user, String newPassword) {
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        user.updatePassword(encodedNewPassword);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UserEntity user) {
        if (user.isDeleted()) {
            throw new InvalidValueException(ErrorCode.ALREADY_DELETED_USER);
        }

        user.delete();
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NonExistentException(ErrorCode.NONEXISTENT_USER));

        return new UserAdapter(user);
    }
}
