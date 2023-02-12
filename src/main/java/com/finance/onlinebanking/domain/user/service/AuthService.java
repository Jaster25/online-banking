package com.finance.onlinebanking.domain.user.service;

import com.finance.onlinebanking.domain.user.dto.LogInRequestDto;
import com.finance.onlinebanking.domain.user.dto.LogInResponseDto;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.global.config.security.UserAdapter;
import com.finance.onlinebanking.global.config.security.jwt.TokenProvider;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public LogInResponseDto logIn(LogInRequestDto logInRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        Authentication authentication = null;
        UserEntity user = null;

        try {
            user = UserEntity.builder()
                    .username(logInRequestDto.getId())
                    .password(logInRequestDto.getPassword())
                    .build();
            authenticationToken = new UsernamePasswordAuthenticationToken(new UserAdapter(user), logInRequestDto.getPassword());
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new InvalidValueException(ErrorCode.LOGIN_FAILURE);
        }

        String accessToken = tokenProvider.createAccessToken(authentication);

        return LogInResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }
}