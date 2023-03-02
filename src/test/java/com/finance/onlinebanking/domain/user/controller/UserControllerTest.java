package com.finance.onlinebanking.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.onlinebanking.config.WithMockCustomUser;
import com.finance.onlinebanking.domain.user.dto.PasswordRequestDto;
import com.finance.onlinebanking.domain.user.dto.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserControllerTest {

    public static final String PREFIX_URL = "/api/v1/users";

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("회원 가입 API")
    @Nested
    class CreateUserApiTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .id("test1")
                    .password("1234aA@")
                    .name("테스트1")
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(userRequestDto)));

            // then
            result.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(userRequestDto.getId()));
        }

        @DisplayName("실패 - 유효성 검증(이름)")
        @Test
        void failure_nonValidatedUserName() throws Exception {
            // given
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .id("test1")
                    .password("1234aA@")
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(userRequestDto)));

            // then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value("U121"));
        }

        @DisplayName("실패 - 이미 존재하는 사용자 ID")
        @Test
        void failure_duplicatedUserId() throws Exception {
            // given
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .id("user1")
                    .password("1234aA@")
                    .name("김회원")
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(userRequestDto)));

            // then
            result.andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value("U301"));
        }

        @DisplayName("실패 - 이미 로그인한 사용자")
        @Test
        @WithMockCustomUser
        void failure_alreadyLoggedInUser() throws Exception {
            // given
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .id("user5")
                    .password("1234aA@")
                    .name("김회원")
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(userRequestDto)));

            // then
            result.andExpect(status().isForbidden());
        }
    }

    @DisplayName("사용자 통장 목록 조회 API")
    @Nested
    class GetUserPassbooksApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/passbooks"));

            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.passbooks").exists())
                    .andExpect(jsonPath("$.passbooks.size()").value(3));
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/passbooks"));

            // then
            result.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("사용자 비밀번호 변경 API")
    @Nested
    class UpdateUserPasswordApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password("12Bb@@")
                    .build();

            // when
            ResultActions result = mockMvc.perform(put(PREFIX_URL + "/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passwordRequestDto)));

            // then
            result.andExpect(status().isNoContent());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password("12Bb@@")
                    .build();

            // when
            ResultActions result = mockMvc.perform(put(PREFIX_URL + "/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passwordRequestDto)));

            // then
            result.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("회원 탈퇴 API")
    @Nested
    class DeleteUserApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(delete(PREFIX_URL));

            // then
            result.andExpect(status().isNoContent());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(delete(PREFIX_URL));

            // then
            result.andExpect(status().isUnauthorized());
        }
    }
}