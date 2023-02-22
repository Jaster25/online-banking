package com.finance.onlinebanking.domain.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.onlinebanking.config.WithMockCustomAdmin;
import com.finance.onlinebanking.config.WithMockCustomUser;
import com.finance.onlinebanking.domain.bank.dto.BankRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BankControllerTest {

    public static final String PREFIX_URL = "/api/v1/banks";

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

    @DisplayName("은행 생성 API")
    @Nested
    class CreateBankApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomAdmin
        void success() throws Exception {
            // given
            BankRequestDto bankRequestDto = BankRequestDto.builder()
                    .name("B은행")
                    .code("001")
                    .branch("을지로")
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(bankRequestDto)));

            // then
            result.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value("B은행"))
                    .andExpect(jsonPath("$.code").value("001"))
                    .andExpect(jsonPath("$.branch").value("을지로"))
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").doesNotExist());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void fail_notLoggedInUser() throws Exception {
            // given
            BankRequestDto bankRequestDto = BankRequestDto.builder()
                    .name("B은행")
                    .code("001")
                    .branch("을지로")
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(bankRequestDto)));

            // then
            result.andExpect(status().isUnauthorized());
        }

        @DisplayName("실패 - 권한없는 사용자")
        @Test
        @WithMockCustomUser
        void fail_unauthorizedUser() throws Exception {
            // given
            BankRequestDto bankRequestDto = BankRequestDto.builder()
                    .name("B은행")
                    .code("001")
                    .branch("을지로")
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(bankRequestDto)));

            // then
            result.andExpect(status().isForbidden());
        }

        @DisplayName("실패 - 유효성 검증(은행명)")
        @Test
        @WithMockCustomAdmin
        void fail_nonValidatedBankName() throws Exception {
            // given
            BankRequestDto bankRequestDto = BankRequestDto.builder()
                    .code("001")
                    .branch("을지로")
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(bankRequestDto)));

            // then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value("B101"));
        }
    }

    @DisplayName("은행 조회 API")
    @Nested
    class GetBankApiTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/1"));

            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("1"))
                    .andExpect(jsonPath("$.name").value("A은행"))
                    .andExpect(jsonPath("$.code").value("001"))
                    .andExpect(jsonPath("$.branch").value("오목교"))
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").doesNotExist());
        }

        @DisplayName("실패 - 존재하지 않는 은행 ID")
        @Test
        void failure_nonExistentBankId() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/24"));

            // then
            result.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value("B001"));
        }
    }

    @DisplayName("특정 은행 상품 목록 조회 API")
    @Nested
    class GetBankProductsApiTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/1/products"));

            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.passbookProducts").exists())
                    .andExpect(jsonPath("$.passbookProducts.size()").value(2));
        }

        @DisplayName("실패 - 존재하지 않는 은행 ID")
        @Test
        void failure_nonExistentBankId() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/30/products"));

            // then
            result.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value("B001"));
        }
    }
}