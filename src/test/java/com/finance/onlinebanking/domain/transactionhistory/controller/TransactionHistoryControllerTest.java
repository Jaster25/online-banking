package com.finance.onlinebanking.domain.transactionhistory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.onlinebanking.config.WithMockCustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class TransactionHistoryControllerTest {

    public static final String PREFIX_URL = "/api/v1/transactions";

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

    @DisplayName("거래내역 상세 조회 API")
    @Nested
    class GetTransactionHistoryApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/20000"));

            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("20000"))
                    .andExpect(jsonPath("$.withdrawAccountNumber").value("1-003-92834493"))
                    .andExpect(jsonPath("$.depositAccountNumber").value("1-003-30032493"))
                    .andExpect(jsonPath("$.amount").value(2000));
        }

        @DisplayName("실패 - 다른 사용자")
        @Test
        @WithMockCustomUser
        void failure_otherUser() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/20003"));

            // then
            result.andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.code").value("G003"));
        }

        @DisplayName("실패 - 존재하지 않는 거래내역 ID")
        @Test
        @WithMockCustomUser
        void failure_nonExistentTransactionId() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/53"));

            // then
            result.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value("T001"));
        }
    }
}
