package com.finance.onlinebanking.domain.passbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.onlinebanking.config.WithMockCustomAdmin;
import com.finance.onlinebanking.config.WithMockCustomUnAuthorizedUser;
import com.finance.onlinebanking.config.WithMockCustomUser;
import com.finance.onlinebanking.domain.passbook.dto.*;
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

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PassbookControllerTest {

    public static final String PREFIX_URL = "/api/v1/passbooks";

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

    @DisplayName("입출금 통장 개설 API")
    @Nested
    class CreateDepositWithdrawPassbookApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            String pathParameter = "/deposit-withdraw/banks/1/products/1";

            DepositWithdrawPassbookRequestDto depositWithdrawPassbookRequestDto = DepositWithdrawPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.3))
                    .transferLimit(100000L)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(depositWithdrawPassbookRequestDto)));

            // then
            resultActions.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.accountNumber").exists())
                    .andExpect(jsonPath("$.balance").value(0L))
                    .andExpect(jsonPath("$.interestRate").value(BigDecimal.valueOf(1.3)))
                    .andExpect(jsonPath("$.userId").value(11L))
                    .andExpect(jsonPath("$.bankId").exists())
                    .andExpect(jsonPath("$.passbookProductId").exists())
                    .andExpect(jsonPath("$.transferLimit").value(100000L))
                    .andExpect(jsonPath("$.amount").doesNotExist())
                    .andExpect(jsonPath("$.dtype").exists())
                    .andExpect(jsonPath("$.expiredAt").doesNotExist())
                    .andExpect(jsonPath("$.depositDate").exists())
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").doesNotExist());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/deposit-withdraw/banks/1/products/1";

            DepositWithdrawPassbookRequestDto depositWithdrawPassbookRequestDto = DepositWithdrawPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.3))
                    .transferLimit(100000L)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(depositWithdrawPassbookRequestDto)));

            // then
            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("예금 통장 개설 API")
    @Nested
    class CreateFixedDepositPassbookApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            String pathParameter = "/fixed-deposit/banks/1/products/1";

            FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto = FixedDepositPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.3))
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(fixedDepositPassbookRequestDto)));

            // then
            resultActions.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.accountNumber").exists())
                    .andExpect(jsonPath("$.balance").value(0L))
                    .andExpect(jsonPath("$.interestRate").value(BigDecimal.valueOf(1.3)))
                    .andExpect(jsonPath("$.userId").value(11L))
                    .andExpect(jsonPath("$.bankId").exists())
                    .andExpect(jsonPath("$.passbookProductId").exists())
                    .andExpect(jsonPath("$.transferLimit").doesNotExist())
                    .andExpect(jsonPath("$.amount").doesNotExist())
                    .andExpect(jsonPath("$.dtype").exists())
                    .andExpect(jsonPath("$.expiredAt").exists())
                    .andExpect(jsonPath("$.depositDate").exists())
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").doesNotExist());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/fixed-deposit/banks/1/products/1";

            FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto = FixedDepositPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.3))
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(fixedDepositPassbookRequestDto)));

            // then
            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("정기 적금 통장 개설 API")
    @Nested
    class CreateRegularInstallmentPassbookApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            String pathParameter = "/regular-installment/banks/1/products/1";

            RegularInstallmentPassbookRequestDto regularInstallmentPassbookRequestDto = RegularInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.3))
                    .depositDate(15)
                    .amount(1000L)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(regularInstallmentPassbookRequestDto)));

            // then
            resultActions.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.accountNumber").exists())
                    .andExpect(jsonPath("$.balance").value(0L))
                    .andExpect(jsonPath("$.interestRate").value(BigDecimal.valueOf(1.3)))
                    .andExpect(jsonPath("$.userId").value(11L))
                    .andExpect(jsonPath("$.bankId").exists())
                    .andExpect(jsonPath("$.passbookProductId").exists())
                    .andExpect(jsonPath("$.transferLimit").doesNotExist())
                    .andExpect(jsonPath("$.amount").exists())
                    .andExpect(jsonPath("$.dtype").exists())
                    .andExpect(jsonPath("$.expiredAt").exists())
                    .andExpect(jsonPath("$.depositDate").exists())
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").doesNotExist());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/regular-installment/banks/1/products/1";

            RegularInstallmentPassbookRequestDto regularInstallmentPassbookRequestDto = RegularInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.3))
                    .depositDate(15)
                    .amount(1000L)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(regularInstallmentPassbookRequestDto)));

            // then
            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("자유 적금 통장 개설 API")
    @Nested
    class CreateFreeInstallmentPassbookApiTest {
        @DisplayName("성공")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            String pathParameter = "/free-installment/banks/1/products/1";

            FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto = FreeInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.3))
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(freeInstallmentPassbookRequestDto)));

            // then
            resultActions.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.accountNumber").exists())
                    .andExpect(jsonPath("$.balance").value(0L))
                    .andExpect(jsonPath("$.interestRate").value(BigDecimal.valueOf(1.3)))
                    .andExpect(jsonPath("$.userId").value(11L))
                    .andExpect(jsonPath("$.bankId").exists())
                    .andExpect(jsonPath("$.passbookProductId").exists())
                    .andExpect(jsonPath("$.transferLimit").doesNotExist())
                    .andExpect(jsonPath("$.amount").doesNotExist())
                    .andExpect(jsonPath("$.dtype").exists())
                    .andExpect(jsonPath("$.expiredAt").exists())
                    .andExpect(jsonPath("$.depositDate").exists())
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").doesNotExist());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            String pathParameter = "/free-installment/banks/1/products/1";

            FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto = FreeInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.3))
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(freeInstallmentPassbookRequestDto)));

            // then
            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("통장 해지 API")
    @Nested
    class DeletePassbookApiTest {
        @DisplayName("성공 - 통장 소유주에 의한 해지")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            String pathParameter = "/10";

            // when
            ResultActions resultActions = mockMvc.perform(delete(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isNoContent());
        }

        @DisplayName("성공 - 관리자에 의한 해지")
        @Test
        @WithMockCustomAdmin
        void success_byAdmin() throws Exception {
            // given
            String pathParameter = "/10";

            // when
            ResultActions resultActions = mockMvc.perform(delete(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isNoContent());
        }

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 해지")
        @Test
        @WithMockCustomUnAuthorizedUser
        void failure_unAuthorized() throws Exception {
            // given
            String pathParameter = "/10";

            // when
            ResultActions resultActions = mockMvc.perform(delete(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isForbidden());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/10";

            // when
            ResultActions resultActions = mockMvc.perform(delete(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("통장 잔액 조회 API")
    @Nested
    class GetBalanceApiTest {
        @DisplayName("성공 - 통장 소유주에 의한 조회")
        @Test
        @WithMockCustomUser
        void success() throws Exception{
            // given
            String pathParameter = "/10/balance";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.accountNumber").exists())
                    .andExpect(jsonPath("$.balance").exists());
        }

        @DisplayName("성공 - 관리자에 의한 조회")
        @Test
        @WithMockCustomAdmin
        void success_byAdmin() throws Exception {
            // given
            String pathParameter = "/10/balance";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.accountNumber").exists())
                    .andExpect(jsonPath("$.balance").exists());
        }

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 조회")
        @Test
        @WithMockCustomUnAuthorizedUser
        void failure_unAuthorized() throws Exception {
            // given
            String pathParameter = "/10/balance";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isForbidden());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/10/balance";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("통장 상세 조회 API")
    @Nested
    class GetPassbookApiTest {
        @DisplayName("성공 - 통장 소유주에 의한 조회")
        @Test
        @WithMockCustomUser
        void success() throws Exception{
            // given
            String pathParameter = "/10";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then (예시 입출금 통장)
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.accountNumber").exists())
                    .andExpect(jsonPath("$.balance").value(150000L))
                    .andExpect(jsonPath("$.interestRate").value(BigDecimal.valueOf(3.5)))
                    .andExpect(jsonPath("$.userId").value(11L))
                    .andExpect(jsonPath("$.bankId").exists())
                    .andExpect(jsonPath("$.passbookProductId").exists())
                    .andExpect(jsonPath("$.transferLimit").value(1000000L))
                    .andExpect(jsonPath("$.amount").doesNotExist())
                    .andExpect(jsonPath("$.dtype").exists())
                    .andExpect(jsonPath("$.expiredAt").doesNotExist())
                    .andExpect(jsonPath("$.depositDate").exists())
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").doesNotExist());
        }

        @DisplayName("성공 - 관리자에 의한 조회")
        @Test
        @WithMockCustomAdmin
        void success_byAdmin() throws Exception {
            // given
            String pathParameter = "/10";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then (예시 입출금 통장)
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.accountNumber").exists())
                    .andExpect(jsonPath("$.balance").value(150000L))
                    .andExpect(jsonPath("$.interestRate").value(BigDecimal.valueOf(3.5)))
                    .andExpect(jsonPath("$.userId").value(11L))
                    .andExpect(jsonPath("$.bankId").exists())
                    .andExpect(jsonPath("$.passbookProductId").exists())
                    .andExpect(jsonPath("$.transferLimit").value(1000000L))
                    .andExpect(jsonPath("$.amount").doesNotExist())
                    .andExpect(jsonPath("$.dtype").exists())
                    .andExpect(jsonPath("$.expiredAt").doesNotExist())
                    .andExpect(jsonPath("$.depositDate").exists())
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").doesNotExist());
        }

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 조회")
        @Test
        @WithMockCustomUnAuthorizedUser
        void failure_unAuthorized() throws Exception {
            // given
            String pathParameter = "/10";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isForbidden());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/10";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("통장 비밀번호 변경 API")
    @Nested
    class UpdatePassbookPasswordApiTest {
        @DisplayName("성공 - 통장 소유주에 의한 변경")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            String pathParameter = "/10/password";
            String newPassword = "4567";

            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password(newPassword)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passwordRequestDto)));

            // then
            resultActions.andExpect(status().isNoContent());
        }

        @DisplayName("성공 - 관리자에 의한 변경")
        @Test
        @WithMockCustomAdmin
        void success_byAdmin() throws Exception {
            // given
            String pathParameter = "/10/password";
            String newPassword = "4567";

            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password(newPassword)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passwordRequestDto)));

            // then
            resultActions.andExpect(status().isNoContent());
        }

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 변경")
        @Test
        @WithMockCustomUnAuthorizedUser
        void failure_unAuthorized() throws Exception {
            // given
            String pathParameter = "/10/password";
            String newPassword = "4567";

            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password(newPassword)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passwordRequestDto)));

            // then
            resultActions.andExpect(status().isForbidden());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/10/password";
            String newPassword = "4567";

            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password(newPassword)
                    .build();

            // when
            ResultActions result = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(passwordRequestDto)));

            // then
            result.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("이체 한도 변경 API")
    @Nested
    class UpdateTransferLimitApiTest {
        @DisplayName("성공 - 통장 소유주에 의한 변경")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            String pathParameter = "/10/transfer-limit";
            Long newTransferLimit = 300000L;

            TransferLimitRequestDto transferLimitRequestDto = TransferLimitRequestDto.builder()
                    .transferLimit(newTransferLimit)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(transferLimitRequestDto)));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(10L))
                    .andExpect(jsonPath("$.accountNumber").value("1-003-92834493"))
                    .andExpect(jsonPath("$.transferLimit").value(300000L));
        }

        @DisplayName("성공 - 관리자에 의한 변경")
        @Test
        @WithMockCustomAdmin
        void success_byAdmin() throws Exception {
            // given
            String pathParameter = "/10/transfer-limit";
            Long newTransferLimit = 300000L;

            TransferLimitRequestDto transferLimitRequestDto = TransferLimitRequestDto.builder()
                    .transferLimit(newTransferLimit)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(transferLimitRequestDto)));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(10L))
                    .andExpect(jsonPath("$.accountNumber").value("1-003-92834493"))
                    .andExpect(jsonPath("$.transferLimit").value(300000L));
        }

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 변경")
        @Test
        @WithMockCustomUnAuthorizedUser
        void failure_unAuthorized() throws Exception {
            // given
            String pathParameter = "/10/transfer-limit";
            Long newTransferLimit = 300000L;

            TransferLimitRequestDto transferLimitRequestDto = TransferLimitRequestDto.builder()
                    .transferLimit(newTransferLimit)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(transferLimitRequestDto)));

            // then
            resultActions.andExpect(status().isForbidden());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/10/transfer-limit";
            Long newTransferLimit = 300000L;

            TransferLimitRequestDto transferLimitRequestDto = TransferLimitRequestDto.builder()
                    .transferLimit(newTransferLimit)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(transferLimitRequestDto)));

            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("이체 API")
    @Nested
    class CreateTransferApiTest {
        @DisplayName("성공 - 통장 소유주에 의한 이체")
        @Test
        @WithMockCustomUser
        void success() throws Exception {
            // given
            String pathParameter = "/10/transfer/11";
            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(2000L)
                    .memo("이체 테스트 메모")
                    .commission(30L)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(transferRequestDto)));

            // then
            resultActions.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.withdrawAccountNumber").value("1-003-92834493"))
                    .andExpect(jsonPath("$.depositAccountNumber").value("1-003-30032493"))
                    .andExpect(jsonPath("$.amount").value(2000L))
                    .andExpect(jsonPath("$.memo").value("이체 테스트 메모"))
                    .andExpect(jsonPath("$.commission").value(30L))
                    .andExpect(jsonPath("$.createdAt").exists());
        }

        @DisplayName("성공 - 관리자에 의한 이체")
        @Test
        @WithMockCustomAdmin
        void success_byAdmin() throws Exception {
            // given
            String pathParameter = "/10/transfer/11";
            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(2000L)
                    .memo("이체 테스트 메모")
                    .commission(30L)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(transferRequestDto)));

            // then
            resultActions.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.withdrawAccountNumber").value("1-003-92834493"))
                    .andExpect(jsonPath("$.depositAccountNumber").value("1-003-30032493"))
                    .andExpect(jsonPath("$.amount").value(2000L))
                    .andExpect(jsonPath("$.memo").value("이체 테스트 메모"))
                    .andExpect(jsonPath("$.commission").value(30L))
                    .andExpect(jsonPath("$.createdAt").exists());
        }

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 이체")
        @Test
        @WithMockCustomUnAuthorizedUser
        void failure_unAuthorized() throws Exception {
            // given
            String pathParameter = "/10/transfer/11";
            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(2000L)
                    .memo("이체 테스트 메모")
                    .commission(30L)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(post(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(transferRequestDto)));

            // then
            resultActions.andExpect(status().isForbidden());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/10/transfer/11";
            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(2000L)
                    .memo("이체 테스트 메모")
                    .commission(30L)
                    .build();

            // when
            ResultActions resultActions = mockMvc.perform(put(PREFIX_URL + pathParameter)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(objectMapper.writeValueAsString(transferRequestDto)));

            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @DisplayName("통장 거래내역 목록 조회 API")
    @Nested
    class GetPassbookTransactionsApiTest {
        @DisplayName("성공 - 통장 소유주에 의한 조회")
        @Test
        @WithMockCustomUser
        void success_byOwner() throws Exception {
            // given
            String pathParameter = "/10/transactions";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.transactions").exists())
                    .andExpect(jsonPath("$.transactions.size()").value(3));
        }

        @DisplayName("성공 - 관리자에 의한 조회")
        @Test
        @WithMockCustomAdmin
        void success_byAdmin() throws Exception {
            // given
            String pathParameter = "/10/transactions";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.transactions").exists())
                    .andExpect(jsonPath("$.transactions.size()").value(3));
        }

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 조회")
        @Test
        @WithMockCustomUnAuthorizedUser
        void failure_unAuthorized() throws Exception {
            // given
            String pathParameter = "/10/transactions";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isForbidden());
        }

        @DisplayName("실패 - 비로그인")
        @Test
        void failure_notLoggedInUser() throws Exception {
            // given
            String pathParameter = "/10/transactions";

            // when
            ResultActions resultActions = mockMvc.perform(get(PREFIX_URL + pathParameter));

            // then
            resultActions.andExpect(status().isUnauthorized());
        }


    }
}