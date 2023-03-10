package com.finance.onlinebanking.domain.passbook.service;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.passbook.dto.*;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.entity.FixedDepositEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.FreeInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.RegularInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.repository.*;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionsHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import com.finance.onlinebanking.domain.transactionhistory.service.TransactionHistoryService;
import com.finance.onlinebanking.domain.user.entity.Role;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
import com.finance.onlinebanking.global.exception.custom.DuplicatedValueException;
import com.finance.onlinebanking.global.exception.custom.InvalidValueException;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import com.finance.onlinebanking.global.exception.custom.UnAuthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PassbookServiceTest {

    @InjectMocks
    private PassbookService passbookService;

    @Mock
    private PassbookProductRepository passbookProductRepository;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PassbookRepository passbookRepository;

    @Mock
    private DepositWithdrawRepository depositWithdrawRepository;

    @Mock
    private FixedDepositRepository fixedDepositRepository;

    @Mock
    private FreeInstallmentRepository freeInstallmentRepository;

    @Mock
    private RegularInstallmentRepository regularInstallmentRepository;

    @Mock
    private TransactionHistoryService transactionHistoryService;


    @DisplayName("????????? ?????? ??????")
    @Nested
    class CreateDepositWithdrawPassbookTest {
        @DisplayName("??????")
        @Test
        void success() throws Exception {
            // given
            DepositWithdrawPassbookRequestDto depositWithdrawPassbookRequestDto = DepositWithdrawPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .transferLimit(100000L)
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("?????????")
                    .password("123Aa!!!")
                    .name("?????????")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("??????1")
                    .code("101")
                    .branch("??????")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .bank(bankEntity)
                    .term(365)
                    .build();

            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));
            given(passbookRepository.existsByAccountNumber(anyString()))
                    .willReturn(false);

            // when
            PassbookResponseDto passbookResponseDto = passbookService.createDepositWithdrawPassbook(userEntity, bankEntity.getId(), passbookProductEntity.getId(), depositWithdrawPassbookRequestDto);

            // then
            assertEquals(bankEntity.getId(), passbookResponseDto.getBankId());
            assertEquals(passbookProductEntity.getId(), passbookResponseDto.getPassbookProductId());
            assertEquals(userEntity.getId(), passbookResponseDto.getUserId());
            assertEquals(depositWithdrawPassbookRequestDto.getBalance(), passbookResponseDto.getBalance());
            verify(depositWithdrawRepository, times(1)).save(any(DepositWithdrawEntity.class));
        }

        @DisplayName("?????? - ?????? ?????? ?????? ????????? ??????")
        @Test
        void failure_duplicatedDepositWithdrawPassbook() throws Exception {
            // given
            DepositWithdrawPassbookRequestDto depositWithdrawPassbookRequestDto = DepositWithdrawPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .transferLimit(100000L)
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("?????????")
                    .password("123Aa!!!")
                    .name("?????????")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("??????1")
                    .code("101")
                    .branch("??????")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .bank(bankEntity)
                    .term(365)
                    .build();

            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));
            given(passbookRepository.existsByAccountNumber(anyString()))
                    .willReturn(true);

            // when
            // then

            assertThrows(DuplicatedValueException.class,
                    () -> passbookService.createDepositWithdrawPassbook(userEntity, bankEntity.getId(), passbookProductEntity.getId(), depositWithdrawPassbookRequestDto));
        }
    }

    @DisplayName("?????? ?????? ??????")
    @Nested
    class CreateFixedDepositPassbookTest {
        @DisplayName("??????")
        @Test
        void success() throws Exception {
            // given
            FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto = FixedDepositPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("?????????")
                    .password("123Aa!!!")
                    .name("?????????")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("??????1")
                    .code("101")
                    .branch("??????")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .bank(bankEntity)
                    .term(365)
                    .build();

            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));
            given(passbookRepository.existsByAccountNumber(anyString()))
                    .willReturn(false);

            // when
            PassbookResponseDto passbookResponseDto = passbookService.createFixedDepositPassbook(userEntity, bankEntity.getId(), passbookProductEntity.getId(), fixedDepositPassbookRequestDto);

            // then
            assertEquals(bankEntity.getId(), passbookResponseDto.getBankId());
            assertEquals(passbookProductEntity.getId(), passbookResponseDto.getPassbookProductId());
            assertEquals(userEntity.getId(), passbookResponseDto.getUserId());
            assertEquals(fixedDepositPassbookRequestDto.getBalance(), passbookResponseDto.getBalance());
            verify(fixedDepositRepository, times(1)).save(any(FixedDepositEntity.class));
        }

        @DisplayName("?????? - ?????? ?????? ?????? ?????? ??????")
        @Test
        void failure_duplicatedDepositWithdrawPassbook() throws Exception {
            // given
            FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto = FixedDepositPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("?????????")
                    .password("123Aa!!!")
                    .name("?????????")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("??????1")
                    .code("101")
                    .branch("??????")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .bank(bankEntity)
                    .term(365)
                    .build();

            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));
            given(passbookRepository.existsByAccountNumber(anyString()))
                    .willReturn(true);

            // when
            // then

            assertThrows(DuplicatedValueException.class,
                    () -> passbookService.createFixedDepositPassbook(userEntity, bankEntity.getId(), passbookProductEntity.getId(), fixedDepositPassbookRequestDto));
        }
    }

    @DisplayName("?????? ?????? ?????? ??????")
    @Nested
    class CreateRegularInstallmentPassbookTest {
        @DisplayName("??????")
        @Test
        void success() throws Exception {
            // given
            RegularInstallmentPassbookRequestDto regularInstallmentPassbookRequestDto = RegularInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .depositDate(365)
                    .amount(10000L)
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("?????????")
                    .password("123Aa!!!")
                    .name("?????????")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("??????1")
                    .code("101")
                    .branch("??????")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .bank(bankEntity)
                    .term(365)
                    .build();

            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));
            given(passbookRepository.existsByAccountNumber(anyString()))
                    .willReturn(false);

            // when
            PassbookResponseDto passbookResponseDto = passbookService.createRegularInstallmentPassbook(userEntity, bankEntity.getId(), passbookProductEntity.getId(), regularInstallmentPassbookRequestDto);

            // then
            assertEquals(bankEntity.getId(), passbookResponseDto.getBankId());
            assertEquals(passbookProductEntity.getId(), passbookResponseDto.getPassbookProductId());
            assertEquals(userEntity.getId(), passbookResponseDto.getUserId());
            assertEquals(regularInstallmentPassbookRequestDto.getBalance(), passbookResponseDto.getBalance());
            verify(regularInstallmentRepository, times(1)).save(any(RegularInstallmentEntity.class));
        }

        @DisplayName("?????? - ?????? ?????? ?????? ?????? ?????? ??????")
        @Test
        void failure_duplicatedDepositWithdrawPassbook() throws Exception {
            // given
            RegularInstallmentPassbookRequestDto regularInstallmentPassbookRequestDto = RegularInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .depositDate(365)
                    .amount(10000L)
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("?????????")
                    .password("123Aa!!!")
                    .name("?????????")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("??????1")
                    .code("101")
                    .branch("??????")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .bank(bankEntity)
                    .term(365)
                    .build();

            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));
            given(passbookRepository.existsByAccountNumber(anyString()))
                    .willReturn(true);

            // when
            // then
            assertThrows(DuplicatedValueException.class,
                    () -> passbookService.createRegularInstallmentPassbook(userEntity, bankEntity.getId(), passbookProductEntity.getId(), regularInstallmentPassbookRequestDto));
        }
    }

    @DisplayName("?????? ?????? ?????? ??????")
    @Nested
    class CreateFreeInstallmentPassbookTest {
        @DisplayName("??????")
        @Test
        void success() throws Exception {
            // given
            FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto = FreeInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("?????????")
                    .password("123Aa!!!")
                    .name("?????????")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("??????1")
                    .code("101")
                    .branch("??????")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .bank(bankEntity)
                    .term(365)
                    .build();

            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));
            given(passbookRepository.existsByAccountNumber(anyString()))
                    .willReturn(false);

            // when
            PassbookResponseDto passbookResponseDto = passbookService.createFreeInstallmentPassbook(userEntity, bankEntity.getId(), passbookProductEntity.getId(), freeInstallmentPassbookRequestDto);

            // then
            assertEquals(bankEntity.getId(), passbookResponseDto.getBankId());
            assertEquals(passbookProductEntity.getId(), passbookResponseDto.getPassbookProductId());
            assertEquals(userEntity.getId(), passbookResponseDto.getUserId());
            assertEquals(freeInstallmentPassbookRequestDto.getBalance(), passbookResponseDto.getBalance());
            verify(freeInstallmentRepository, times(1)).save(any(FreeInstallmentEntity.class));
        }

        @DisplayName("?????? - ?????? ?????? ?????? ?????? ?????? ??????")
        @Test
        void failure_duplicatedDepositWithdrawPassbook() throws Exception {
            // given
            FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto = FreeInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("?????????")
                    .password("123Aa!!!")
                    .name("?????????")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("??????1")
                    .code("101")
                    .branch("??????")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .bank(bankEntity)
                    .term(365)
                    .build();

            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));
            given(passbookRepository.existsByAccountNumber(anyString()))
                    .willReturn(true);

            // when
            // then
            assertThrows(DuplicatedValueException.class,
                    () -> passbookService.createFreeInstallmentPassbook(userEntity, bankEntity.getId(), passbookProductEntity.getId(), freeInstallmentPassbookRequestDto));
        }
    }

    @DisplayName("?????? ??????")
    @Nested
    class DeletePassbookTest {
        @DisplayName("?????? - ?????? ???????????? ??????")
        @Test
        void success_byOwner() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .isDeleted(true)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            //when
            passbookService.deletePassbook(userEntity, depositWithdrawEntity.getId());

            //then
            assertTrue(depositWithdrawEntity.isDeleted());
        }

        @DisplayName("?????? - ???????????? ?????? ??????")
        @Test
        void success_byAdmin() throws Exception {
            // given
            // ????????? ?????? ?????????
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity.addRole(Role.USER);

            // ?????????
            UserEntity adminUserEntity = UserEntity.builder()
                    .id(10L)
                    .build();
            adminUserEntity.addRole(Role.ADMIN);
            adminUserEntity.addRole(Role.USER);

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .isDeleted(true)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            //when
            passbookService.deletePassbook(adminUserEntity, depositWithdrawEntity.getId());
            //then
            assertTrue(depositWithdrawEntity.isDeleted());
        }

        @DisplayName("?????? - ?????? ????????? ??????")
        @Test
        void failure_alreadyDeletedPassbook() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class, () -> passbookService.deletePassbook(userEntity, 1L));
        }

        @DisplayName("?????? - ????????? ?????? ?????? ?????????")
        @Test
        void failure_unAuthorizedUser() throws Exception {
            // given
            // ?????? ????????? ?????? ???????????? ?????????
            UserEntity userEntity1 = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity1.addRole(Role.USER);

            // ?????? ????????? ???????????? ????????? ???????????? ?????? ?????????
            UserEntity userEntity2 = UserEntity.builder()
                    .id(12L)
                    .build();
            userEntity2.addRole(Role.USER);

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .isDeleted(true)
                    .dtype("DW")
                    .user(userEntity1)
                    .build();

            // when
            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // then
            assertThrows(UnAuthorizedException.class, () -> passbookService.deletePassbook(userEntity2, depositWithdrawEntity.getId()));
        }
    }

    @DisplayName("?????? ??????")
    @Nested
    class GetBalanceTest {
        @DisplayName("?????? - ?????? ???????????? ?????? ??????")
        @Test
        void success_byOwner() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .isDeleted(true)
                    .balance(10000L)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            //when
            PassbookBalanceResponseDto passbookBalanceResponseDto = passbookService.getBalance(userEntity, depositWithdrawEntity.getId());

            //then
            assertEquals(depositWithdrawEntity.getBalance(), passbookBalanceResponseDto.getBalance());
        }

        @DisplayName("?????? - ???????????? ?????? ??????")
        @Test
        void success_byAdmin() throws Exception {
            // given
            // ????????? ?????? ?????????
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity.addRole(Role.USER);

            // ?????????
            UserEntity adminUserEntity = UserEntity.builder()
                    .id(10L)
                    .build();
            adminUserEntity.addRole(Role.ADMIN);
            adminUserEntity.addRole(Role.USER);

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .balance(10000L)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            //when
            PassbookBalanceResponseDto passbookBalanceResponseDto = passbookService.getBalance(adminUserEntity, depositWithdrawEntity.getId());

            //then
            assertEquals(depositWithdrawEntity.getBalance(), passbookBalanceResponseDto.getBalance());
        }

        @DisplayName("?????? - ????????? ????????? ?????? ??????")
        @Test
        void failure_alreadyDeletedPassbook() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());
            // when
            // then
            assertThrows(NonExistentException.class, () -> passbookService.getBalance(userEntity, 1L));
        }

        @DisplayName("?????? - ????????? ?????? ?????? ???????????? ?????? ??????")
        @Test
        void failure_unAuthorizedUser() throws Exception {
            // given
            // ?????? ????????? ?????? ???????????? ?????????
            UserEntity userEntity1 = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity1.addRole(Role.USER);

            // ?????? ????????? ???????????? ????????? ???????????? ?????? ?????????
            UserEntity userEntity2 = UserEntity.builder()
                    .id(12L)
                    .build();
            userEntity2.addRole(Role.USER);

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .dtype("DW")
                    .user(userEntity1)
                    .build();

            // when
            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // then
            assertThrows(UnAuthorizedException.class, () -> passbookService.getBalance(userEntity2, depositWithdrawEntity.getId()));
        }
    }

    @DisplayName("?????? ?????? ??????")
    @Nested
    class GetPassbookTest {
        @DisplayName("?????? - ?????? ???????????? ?????? ?????? ??????")
        @Test
        void success_byOwner() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .bank(bankEntity)
                    .passbookProduct(passbookProductEntity)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            //when
            PassbookResponseDto passbookResponseDto = passbookService.getPassbook(userEntity, 2L);

            //then
            assertEquals(depositWithdrawEntity.getAccountNumber(), passbookResponseDto.getAccountNumber());
        }

        @DisplayName("?????? - ???????????? ?????? ?????? ??????")
        @Test
        void success_byAdmin() throws Exception {
            // given
            // ????????? ?????? ?????????
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity.addRole(Role.USER);

            // ?????????
            UserEntity adminUserEntity = UserEntity.builder()
                    .id(10L)
                    .build();
            adminUserEntity.addRole(Role.ADMIN);
            adminUserEntity.addRole(Role.USER);

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .id(1L)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .bank(bankEntity)
                    .passbookProduct(passbookProductEntity)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            //when
            PassbookResponseDto passbookResponseDto = passbookService.getPassbook(adminUserEntity, depositWithdrawEntity.getId());

            //then
            assertEquals(depositWithdrawEntity.getAccountNumber(), passbookResponseDto.getAccountNumber());
        }

        @DisplayName("?????? - ????????? ????????? ?????? ?????? ??????")
        @Test
        void failure_alreadyDeletedPassbook() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());
            // when
            // then
            assertThrows(NonExistentException.class, () -> passbookService.getPassbook(userEntity, 2L));
        }

        @DisplayName("?????? - ????????? ?????? ?????? ???????????? ?????? ??????")
        @Test
        void failure_unAuthorizedUser() throws Exception {
            // given
            // ?????? ????????? ?????? ???????????? ?????????
            UserEntity userEntity1 = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity1.addRole(Role.USER);

            // ?????? ????????? ???????????? ????????? ???????????? ?????? ?????????
            UserEntity userEntity2 = UserEntity.builder()
                    .id(12L)
                    .build();
            userEntity2.addRole(Role.USER);

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-001-1843986381")
                    .dtype("DW")
                    .user(userEntity1)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            // then
            assertThrows(UnAuthorizedException.class, () -> passbookService.getPassbook(userEntity2, depositWithdrawEntity.getId()));
        }
    }

    @DisplayName("?????? ?????? ??????")
    @Nested
    class GetPassbooksTest {
        @DisplayName("??????")
        @Test
        void success_byOwner() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .name("?????? A")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .name("?????? A")
                    .build();

            DepositWithdrawEntity depositWithdrawEntity1 = DepositWithdrawEntity.builder()
                    .accountNumber("1-001-1843986381")
                    .bank(bankEntity)
                    .passbookProduct(passbookProductEntity)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity2 = DepositWithdrawEntity.builder()
                    .accountNumber("2-001-1843986381")
                    .bank(bankEntity)
                    .passbookProduct(passbookProductEntity)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity3 = DepositWithdrawEntity.builder()
                    .accountNumber("3-001-1843986381")
                    .bank(bankEntity)
                    .passbookProduct(passbookProductEntity)
                    .dtype("DW")
                    .user(userEntity)
                    .build();

            given(passbookRepository.findAllByUserAndIsDeletedFalse(userEntity))
                    .willReturn(List.of(depositWithdrawEntity1, depositWithdrawEntity2, depositWithdrawEntity3));

            // when
            PassbooksResponseDto passbooksResponseDto = passbookService.getPassbooks(userEntity);

            //then
            assertEquals(3, passbooksResponseDto.getPassbooks().size());
        }
    }

    @DisplayName("?????? ???????????? ??????")
    @Nested
    class UpdatePasswordTest {
        @DisplayName("?????? - ?????? ???????????? ?????? ??????")
        @Test
        void success_byOwner() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            String password = "111Aa!!!";
            String newPassword = "444Aa!!!";
            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .user(userEntity)
                    .password(password)
                    .build();

            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password(newPassword)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            passbookService.updatePassword(userEntity, depositWithdrawEntity.getId(), passwordRequestDto);

            // then
            assertEquals(newPassword, depositWithdrawEntity.getPassword());
        }

        @DisplayName("?????? - ???????????? ?????? ?????? ???????????? ??????")
        @Test
        void success_byAdmin() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity.addRole(Role.USER);

            UserEntity adminUserEntity = UserEntity.builder()
                    .username("admin1")
                    .build();
            adminUserEntity.addRole(Role.ADMIN);

            String password = "111Aa!!!";
            String newPassword = "444Aa!!!";
            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .user(userEntity)
                    .password(password)
                    .build();

            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password(newPassword)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            passbookService.updatePassword(adminUserEntity, depositWithdrawEntity.getId(), passwordRequestDto);

            // then
            assertEquals(newPassword, depositWithdrawEntity.getPassword());
        }

        @DisplayName("?????? - ????????? ????????? ?????? ??????")
        @Test
        void failure_alreadyDeletedPassbook() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password("111Aa!!")
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());
            // when
            // then
            assertThrows(NonExistentException.class, () -> passbookService.updatePassword(userEntity, 2L, passwordRequestDto));
        }

        @DisplayName("?????? - ?????? ?????? ?????? ???????????? ?????? ??????")
        @Test
        void failure_unAuthorizedUser() throws Exception {
            // ?????? ?????????
            UserEntity userEntity1 = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity1.addRole(Role.USER);

            // ?????? ???????????? ????????? ???????????? ?????? ?????????
            UserEntity userEntity2 = UserEntity.builder()
                    .username("user2")
                    .build();
            userEntity2.addRole(Role.USER);

            String password = "111Aa!!!";
            String newPassword = "444Aa!!!";
            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .user(userEntity1)
                    .password(password)
                    .build();

            PasswordRequestDto passwordRequestDto = PasswordRequestDto.builder()
                    .password(newPassword)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            // then
            assertThrows(UnAuthorizedException.class, () -> passbookService.updatePassword(userEntity2, 1L, passwordRequestDto));
        }
    }

    @DisplayName("?????? ?????? ??????")
    @Nested
    class UpdateTransferLimitTest {
        @DisplayName("?????? - ?????? ???????????? ?????? ??????")
        @Test
        void success_byOwner() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            Long transferLimit = 100000L;
            Long newTransferLimit = 50000L;
            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .user(userEntity)
                    .transferLimit(transferLimit)
                    .dtype("DW")
                    .build();

            TransferLimitRequestDto transferLimitRequestDto = TransferLimitRequestDto.builder()
                    .transferLimit(newTransferLimit)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            TransferLimitResponseDto transferLimitResponseDto = passbookService.updateTransferLimit(userEntity, depositWithdrawEntity.getId(), transferLimitRequestDto);

            // then
            assertEquals(newTransferLimit, transferLimitResponseDto.getTransferLimit());
        }

        @DisplayName("?????? - ???????????? ?????? ??????")
        @Test
        void success_byAdmin() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity.addRole(Role.USER);

            UserEntity adminUserEntity = UserEntity.builder()
                    .username("admin1")
                    .build();
            adminUserEntity.addRole(Role.ADMIN);

            Long transferLimit = 100000L;
            Long newTransferLimit = 50000L;
            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .user(userEntity)
                    .transferLimit(transferLimit)
                    .dtype("DW")
                    .build();

            TransferLimitRequestDto transferLimitRequestDto = TransferLimitRequestDto.builder()
                    .transferLimit(newTransferLimit)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            TransferLimitResponseDto transferLimitResponseDto = passbookService.updateTransferLimit(adminUserEntity, depositWithdrawEntity.getId(), transferLimitRequestDto);

            // then
            assertEquals(newTransferLimit, transferLimitResponseDto.getTransferLimit());
        }

        @DisplayName("?????? - ????????? ????????? ?????? ??????")
        @Test
        void failure_alreadyDeletedPassbook() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            TransferLimitRequestDto transferLimitRequestDto = TransferLimitRequestDto.builder()
                    .transferLimit(50000L)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class, () -> passbookService.updateTransferLimit(userEntity, 2L, transferLimitRequestDto));
        }

        @DisplayName("?????? - ?????? ?????? ?????? ???????????? ?????? ??????")
        @Test
        void failure_unAuthorizedUser() throws Exception {
            // given
            // ?????? ?????????
            UserEntity userEntity1 = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity1.addRole(Role.USER);

            // ?????? ???????????? ????????? ???????????? ?????? ?????????
            UserEntity userEntity2 = UserEntity.builder()
                    .username("user2")
                    .build();
            userEntity2.addRole(Role.USER);

            Long transferLimit = 100000L;
            Long newTransferLimit = 50000L;
            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .user(userEntity1)
                    .transferLimit(transferLimit)
                    .dtype("DW")
                    .build();

            TransferLimitRequestDto transferLimitRequestDto = TransferLimitRequestDto.builder()
                    .transferLimit(newTransferLimit)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            // then
            assertThrows(UnAuthorizedException.class, () -> passbookService.updateTransferLimit(userEntity2, 1L, transferLimitRequestDto));
        }
    }

    @DisplayName("??????")
    @Nested
    class CreateTransferTest {
        @DisplayName("?????? - ?????? ???????????? ?????? ??????")
        @Test
        void success_byOwner() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            DepositWithdrawEntity withdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .user(userEntity)
                    .balance(5000L)
                    .transferLimit(10000L)
                    .dtype("DW")
                    .build();

            FixedDepositEntity depositEntity = FixedDepositEntity.builder()
                    .id(2L)
                    .accountNumber("1-003-30032493")
                    .user(userEntity)
                    .balance(0L)
                    .dtype("FW")
                    .build();

            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(3000L)
                    .memo("?????? ?????? ????????? ??????")
                    .commission(100L)
                    .build();

            TransactionHistoryResponseDto transactionHistoryResponseDto = TransactionHistoryResponseDto.builder()
                    .id(1L)
                    .withdrawAccountNumber(withdrawEntity.getAccountNumber())
                    .depositAccountNumber(depositEntity.getAccountNumber())
                    .amount(transferRequestDto.getAmount())
                    .memo(transferRequestDto.getMemo())
                    .commission(transferRequestDto.getCommission())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(null)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalseForUpdate(anyLong()))
                    .willReturn(Optional.of(withdrawEntity), Optional.of(depositEntity));
            given(transactionHistoryService.createTransactionHistory(any(), any(), any()))
                    .willReturn(transactionHistoryResponseDto);

            // when
            TransferResponseDto transferResponseDto = passbookService.createTransfer(userEntity, 1L, 2L, transferRequestDto);

            // then
            assertEquals(3000L, transferResponseDto.getAmount());
            assertEquals(2000L, withdrawEntity.getBalance());
            assertEquals(3000L, depositEntity.getBalance());
        }

        @DisplayName("?????? - ???????????? ?????? ??????")
        @Test
        void success_byAdmin() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity.addRole(Role.USER);

            UserEntity adminUserEntity = UserEntity.builder()
                    .username("admin1")
                    .build();
            adminUserEntity.addRole(Role.ADMIN);

            DepositWithdrawEntity withdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .user(userEntity)
                    .balance(5000L)
                    .transferLimit(10000L)
                    .dtype("DW")
                    .build();

            FixedDepositEntity depositEntity = FixedDepositEntity.builder()
                    .id(2L)
                    .accountNumber("1-003-30032493")
                    .user(userEntity)
                    .balance(0L)
                    .dtype("FW")
                    .build();

            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(3000L)
                    .memo("?????? ?????? ????????? ??????")
                    .commission(100L)
                    .build();

            TransactionHistoryResponseDto transactionHistoryResponseDto = TransactionHistoryResponseDto.builder()
                    .id(1L)
                    .withdrawAccountNumber(withdrawEntity.getAccountNumber())
                    .depositAccountNumber(depositEntity.getAccountNumber())
                    .amount(transferRequestDto.getAmount())
                    .memo(transferRequestDto.getMemo())
                    .commission(transferRequestDto.getCommission())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(null)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalseForUpdate(anyLong()))
                    .willReturn(Optional.of(withdrawEntity), Optional.of(depositEntity));
            given(transactionHistoryService.createTransactionHistory(any(), any(), any()))
                    .willReturn(transactionHistoryResponseDto);

            // when
            TransferResponseDto transferResponseDto = passbookService.createTransfer(userEntity, 1L, 2L, transferRequestDto);

            // then
            assertEquals(3000L, transferResponseDto.getAmount());
            assertEquals(2000L, withdrawEntity.getBalance());
            assertEquals(3000L, depositEntity.getBalance());
        }

        @DisplayName("?????? - ????????? ?????? ????????? ????????? ??????")
        @Test
        void failure_alreadyDeletedWithdrawPassbook() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(3000L)
                    .memo("?????? ?????? ????????? ??????")
                    .commission(100L)
                    .build();

            DepositWithdrawEntity withdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .user(userEntity)
                    .balance(5000L)
                    .transferLimit(10000L)
                    .dtype("DW")
                    .build();

            FixedDepositEntity depositEntity = FixedDepositEntity.builder()
                    .id(2L)
                    .accountNumber("1-003-30032493")
                    .user(userEntity)
                    .balance(0L)
                    .dtype("FW")
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalseForUpdate(anyLong()))
                    .willReturn(Optional.empty(), Optional.of(depositEntity));

            // when
            // then
            assertThrows(NonExistentException.class, () -> passbookService.createTransfer(userEntity, 1L,2L, transferRequestDto));
        }

        @DisplayName("?????? - ????????? ?????? ????????? ????????? ??????")
        @Test
        void failure_alreadyDeletedDepositPassbook() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(3000L)
                    .memo("?????? ?????? ????????? ??????")
                    .commission(100L)
                    .build();

            DepositWithdrawEntity withdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .user(userEntity)
                    .balance(5000L)
                    .transferLimit(10000L)
                    .dtype("DW")
                    .build();

            FixedDepositEntity depositEntity = FixedDepositEntity.builder()
                    .id(2L)
                    .accountNumber("1-003-30032493")
                    .user(userEntity)
                    .balance(0L)
                    .dtype("FW")
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalseForUpdate(anyLong()))
                    .willReturn(Optional.of(withdrawEntity), Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class, () -> passbookService.createTransfer(userEntity, 1L,2L, transferRequestDto));
        }

        @DisplayName("?????? - ?????? ?????? ?????? ???????????? ?????? ??????")
        @Test
        void failure_unAuthorizedUser() throws Exception {
            // ?????? ?????????
            UserEntity userEntity1 = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity1.addRole(Role.USER);

            // ?????? ???????????? ????????? ???????????? ?????? ?????????
            UserEntity userEntity2 = UserEntity.builder()
                    .username("user2")
                    .build();
            userEntity2.addRole(Role.USER);

            DepositWithdrawEntity withdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .user(userEntity1)
                    .balance(5000L)
                    .transferLimit(10000L)
                    .dtype("DW")
                    .build();

            FixedDepositEntity depositEntity = FixedDepositEntity.builder()
                    .id(2L)
                    .accountNumber("1-003-30032493")
                    .user(userEntity1)
                    .balance(0L)
                    .dtype("FW")
                    .build();

            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(3000L)
                    .memo("?????? ?????? ????????? ??????")
                    .commission(100L)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalseForUpdate(anyLong()))
                    .willReturn(Optional.of(withdrawEntity), Optional.of(depositEntity));

            // when
            // then
            assertThrows(UnAuthorizedException.class, () -> passbookService.createTransfer((userEntity2), 1L, 2L, transferRequestDto));
        }

        @DisplayName("?????? - ?????? ????????? ?????? ??????")
        @Test
        void failure_lackOfBalance() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            DepositWithdrawEntity withdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .user(userEntity)
                    .balance(0L)
                    .transferLimit(10000L)
                    .dtype("DW")
                    .build();

            FixedDepositEntity depositEntity = FixedDepositEntity.builder()
                    .id(2L)
                    .accountNumber("1-003-30032493")
                    .user(userEntity)
                    .balance(0L)
                    .dtype("FW")
                    .build();

            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(3000L)
                    .memo("?????? ?????? ????????? ??????")
                    .commission(100L)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalseForUpdate(anyLong()))
                    .willReturn(Optional.of(withdrawEntity), Optional.of(depositEntity));

            // when
            // then
            assertThrows(InvalidValueException.class, () -> passbookService.createTransfer(userEntity, 1L, 2L, transferRequestDto));
        }

        @DisplayName("?????? - ?????? ????????? ?????? ?????? ??????")
        @Test
        void failure_excessTransferLimit() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            DepositWithdrawEntity withdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .user(userEntity)
                    .balance(0L)
                    .transferLimit(10000L)
                    .dtype("DW")
                    .build();

            FixedDepositEntity depositEntity = FixedDepositEntity.builder()
                    .id(2L)
                    .accountNumber("1-003-30032493")
                    .user(userEntity)
                    .balance(0L)
                    .dtype("FW")
                    .build();

            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .amount(50000L)
                    .memo("?????? ?????? ????????? ??????")
                    .commission(100L)
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalseForUpdate(anyLong()))
                    .willReturn(Optional.of(withdrawEntity), Optional.of(depositEntity));

            // when
            // then
            assertThrows(InvalidValueException.class, () -> passbookService.createTransfer(userEntity, 1L, 2L, transferRequestDto));
        }
    }

    @DisplayName("?????? ???????????? ?????? ??????")
    @Nested
    class GetPassbookTransactionsTest {
        @DisplayName("?????? - ?????? ???????????? ?????? ??????")
        @Test
        void success_byOwner() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            TransactionHistoryEntity transactionHistoryEntity1 = TransactionHistoryEntity.builder()
                    .id(1L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(1000L)
                    .memo("?????? ?????? 1")
                    .commission(100L)
                    .build();

            TransactionHistoryEntity transactionHistoryEntity2 = TransactionHistoryEntity.builder()
                    .id(2L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(2000L)
                    .memo("?????? ?????? 2")
                    .commission(100L)
                    .build();

            TransactionHistoryEntity transactionHistoryEntity3 = TransactionHistoryEntity.builder()
                    .id(3L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(3000L)
                    .memo("?????? ?????? 3")
                    .commission(100L)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .dtype("DW")
                    .user(userEntity)
                    .withdrawTransactionHistories(List.of(transactionHistoryEntity1, transactionHistoryEntity2, transactionHistoryEntity3))
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            TransactionsHistoryResponseDto transactionsHistoryResponseDto = passbookService.getPassbookTransactions(userEntity, 1L);

            // then
            assertEquals(3, transactionsHistoryResponseDto.getTransactions().size());
        }

        @DisplayName("?????? - ???????????? ?????? ??????")
        @Test
        void success_byAdmin() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity.addRole(Role.USER);

            UserEntity adminEntity = UserEntity.builder()
                    .username("admin1")
                    .build();
            adminEntity.addRole(Role.ADMIN);

            TransactionHistoryEntity transactionHistoryEntity1 = TransactionHistoryEntity.builder()
                    .id(1L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(1000L)
                    .memo("?????? ?????? 1")
                    .commission(100L)
                    .build();

            TransactionHistoryEntity transactionHistoryEntity2 = TransactionHistoryEntity.builder()
                    .id(2L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(2000L)
                    .memo("?????? ?????? 2")
                    .commission(100L)
                    .build();

            TransactionHistoryEntity transactionHistoryEntity3 = TransactionHistoryEntity.builder()
                    .id(3L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(3000L)
                    .memo("?????? ?????? 3")
                    .commission(100L)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .dtype("DW")
                    .user(userEntity)
                    .withdrawTransactionHistories(List.of(transactionHistoryEntity1, transactionHistoryEntity2, transactionHistoryEntity3))
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            TransactionsHistoryResponseDto transactionsHistoryResponseDto = passbookService.getPassbookTransactions(adminEntity, 1L);

            // then
            assertEquals(3, transactionsHistoryResponseDto.getTransactions().size());
        }

        @DisplayName("?????? - ????????? ????????? ?????? ??????")
        @Test
        void failure_alreadyDeletedPassbook() throws Exception {
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class, () -> passbookService.getPassbookTransactions(userEntity, 1L));
        }

        @DisplayName("?????? - ?????? ?????? ?????? ???????????? ?????? ??????")
        @Test
        void failure_unAuthorizedUser() throws Exception {
            // given
            UserEntity userEntity1 = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity1.addRole(Role.USER);

            UserEntity userEntity2 = UserEntity.builder()
                    .username("user2")
                    .build();
            userEntity2.addRole(Role.USER);

            TransactionHistoryEntity transactionHistoryEntity1 = TransactionHistoryEntity.builder()
                    .id(1L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(1000L)
                    .memo("?????? ?????? 1")
                    .commission(100L)
                    .build();

            TransactionHistoryEntity transactionHistoryEntity2 = TransactionHistoryEntity.builder()
                    .id(2L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(2000L)
                    .memo("?????? ?????? 2")
                    .commission(100L)
                    .build();

            TransactionHistoryEntity transactionHistoryEntity3 = TransactionHistoryEntity.builder()
                    .id(3L)
                    .withdrawAccountNumber("1-003-92834493")
                    .depositAccountNumber("1-003-30032493")
                    .amount(3000L)
                    .memo("?????? ?????? 3")
                    .commission(100L)
                    .build();

            DepositWithdrawEntity depositWithdrawEntity = DepositWithdrawEntity.builder()
                    .id(1L)
                    .accountNumber("1-003-92834493")
                    .dtype("DW")
                    .user(userEntity1)
                    .withdrawTransactionHistories(List.of(transactionHistoryEntity1, transactionHistoryEntity2, transactionHistoryEntity3))
                    .build();

            given(passbookRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(depositWithdrawEntity));

            // when
            // then
            assertThrows(UnAuthorizedException.class, () -> passbookService.getPassbookTransactions(userEntity2, 1L));
        }
    }
}