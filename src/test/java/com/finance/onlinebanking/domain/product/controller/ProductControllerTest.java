package com.finance.onlinebanking.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finance.onlinebanking.config.WithMockCustomAdmin;
import com.finance.onlinebanking.config.WithMockCustomUser;
import com.finance.onlinebanking.domain.product.dto.PassbookProductRequestDto;
import com.finance.onlinebanking.domain.product.dto.PassbookProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ProductControllerTest {
    
    public static final String PREFIX_URL = "/api/v1/products";

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
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @DisplayName("?????? ?????? API")
    @Nested
    class CreateProductApiTest {
        @DisplayName("??????")
        @Test
        @WithMockCustomAdmin
        void success() throws Exception {
            // given
            PassbookProductRequestDto passbookProductRequestDto = PassbookProductRequestDto.builder()
                    .name("????????? ?????? ??????")
                    .startedAt(LocalDateTime.now().plusWeeks(3))
                    .endedAt(LocalDateTime.now().plusWeeks(10))
                    .interestRate(BigDecimal.valueOf(2.5))
                    .benefit("????????? ??????")
                    .content("????????? ??????")
                    .conditions("??????")
                    .term(750)
                    .amount(30000L)
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passbookProductRequestDto)));
            
            // then
            result.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.bankId").value("1"))
                    .andExpect(jsonPath("$.benefit").value(passbookProductRequestDto.getBenefit()))
                    .andExpect(jsonPath("$.content").value(passbookProductRequestDto.getContent()));
        }

        @DisplayName("?????? - ?????? ?????????")
        @Test
        @WithMockCustomUser
        void failure_nonAuthorizedUser() throws Exception {
            // given
            PassbookProductRequestDto passbookProductRequestDto = PassbookProductRequestDto.builder()
                    .name("????????? ?????? ??????")
                    .startedAt(LocalDateTime.now().plusWeeks(3))
                    .endedAt(LocalDateTime.now().plusWeeks(10))
                    .interestRate(BigDecimal.valueOf(2.5))
                    .benefit("????????? ??????")
                    .content("????????? ??????")
                    .conditions("??????")
                    .term(750)
                    .amount(30000L)
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passbookProductRequestDto)));

            // then
            result.andExpect(status().isForbidden());
        }

        @DisplayName("?????? - ????????? ??????(?????? ??????)")
        @Test
        @WithMockCustomAdmin
        void failure_nonValidatedProductName() throws Exception {
            // given
            PassbookProductRequestDto passbookProductRequestDto = PassbookProductRequestDto.builder()
                    .startedAt(LocalDateTime.now().plusWeeks(3))
                    .endedAt(LocalDateTime.now().plusWeeks(10))
                    .interestRate(BigDecimal.valueOf(2.5))
                    .benefit("????????? ??????")
                    .content("????????? ??????")
                    .conditions("??????")
                    .term(750)
                    .amount(30000L)
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passbookProductRequestDto)));

            // then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value("R102"));
        }

        @DisplayName("?????? - ???????????? ?????? ?????? ID")
        @Test
        @WithMockCustomAdmin
        void failure_nonExistentBankId() throws Exception {
            // given
            PassbookProductRequestDto passbookProductRequestDto = PassbookProductRequestDto.builder()
                    .name("????????? ?????? ??????")
                    .startedAt(LocalDateTime.now().plusWeeks(3))
                    .endedAt(LocalDateTime.now().plusWeeks(10))
                    .interestRate(BigDecimal.valueOf(2.5))
                    .benefit("????????? ??????")
                    .content("????????? ??????")
                    .conditions("??????")
                    .term(750)
                    .amount(30000L)
                    .build();

            // when
            ResultActions result = mockMvc.perform(post(PREFIX_URL + "/5")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passbookProductRequestDto)));

            // then
            result.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value("B001"));
        }
   }

    @DisplayName("?????? ?????? ?????? API")
    @Nested
    class GetProductApiTest {
        @DisplayName("??????")
        @Test
        @WithMockCustomAdmin
        void success() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/1"));

            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("1"))
                    .andExpect(jsonPath("$.bankId").value("1"))
                    .andExpect(jsonPath("$.name").value("??????????????? ????????? ??????"))
                    .andExpect(jsonPath("$.interestRate").value("3.6"));
        }

        @DisplayName("?????? - ???????????? ?????? ?????? ID")
        @Test
        void failure_nonExistentProductId() throws Exception {
            // given
            // when
            ResultActions result = mockMvc.perform(get(PREFIX_URL + "/16"));

            // then
            result.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value("R001"));
        }
    }
}