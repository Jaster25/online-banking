package com.finance.onlinebanking.domain.passbook.service;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.passbook.dto.*;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.entity.FixedDepositEntity;
import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.FreeInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.entity.installment.RegularInstallmentEntity;
import com.finance.onlinebanking.domain.passbook.repository.*;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
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


    @DisplayName("입출금 통장 생성")
    @Nested
    class CreateDepositWithdrawPassbookTest {
        @DisplayName("성공")
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
                    .username("김회원")
                    .password("123Aa!!!")
                    .name("홍길동")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("은행1")
                    .code("101")
                    .branch("태릉")
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

        @DisplayName("실패 - 이미 존재 하는 입출금 통장")
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
                    .username("김회원")
                    .password("123Aa!!!")
                    .name("홍길동")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("은행1")
                    .code("101")
                    .branch("태릉")
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

    @DisplayName("예금 통장 생성")
    @Nested
    class CreateFixedDepositPassbookTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto = FixedDepositPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("김회원")
                    .password("123Aa!!!")
                    .name("홍길동")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("은행1")
                    .code("101")
                    .branch("태릉")
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

        @DisplayName("실패 - 이미 존재 하는 예금 통장 통장")
        @Test
        void failure_duplicatedDepositWithdrawPassbook() throws Exception {
            // given
            FixedDepositPassbookRequestDto fixedDepositPassbookRequestDto = FixedDepositPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("김회원")
                    .password("123Aa!!!")
                    .name("홍길동")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("은행1")
                    .code("101")
                    .branch("태릉")
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

    @DisplayName("정기 적금 통장 생성")
    @Nested
    class CreateRegularInstallmentPassbookTest {
        @DisplayName("성공")
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
                    .username("김회원")
                    .password("123Aa!!!")
                    .name("홍길동")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("은행1")
                    .code("101")
                    .branch("태릉")
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

        @DisplayName("실패 - 이미 존재 하는 정기 적금 통장 통장")
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
                    .username("김회원")
                    .password("123Aa!!!")
                    .name("홍길동")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("은행1")
                    .code("101")
                    .branch("태릉")
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

    @DisplayName("자유 적금 통장 생성")
    @Nested
    class CreateFreeInstallmentPassbookTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto = FreeInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("김회원")
                    .password("123Aa!!!")
                    .name("홍길동")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("은행1")
                    .code("101")
                    .branch("태릉")
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

        @DisplayName("실패 - 이미 존재 하는 자유 적금 통장 통장")
        @Test
        void failure_duplicatedDepositWithdrawPassbook() throws Exception {
            // given
            FreeInstallmentPassbookRequestDto freeInstallmentPassbookRequestDto = FreeInstallmentPassbookRequestDto.builder()
                    .password("1234")
                    .balance(0L)
                    .interestRate(BigDecimal.valueOf(1.0))
                    .build();

            UserEntity userEntity = UserEntity.builder()
                    .username("김회원")
                    .password("123Aa!!!")
                    .name("홍길동")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("은행1")
                    .code("101")
                    .branch("태릉")
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

    @DisplayName("통장 해지")
    @Nested
    class DeletePassbookTest {
        @DisplayName("성공 - 통장 소유주의 해지")
        @Test
        void success_byOwner() {
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

        @DisplayName("성공 - 관리자의 통장 해지")
        @Test
        void success_byAdmin() {
            // given
            // 통장의 원래 소유주
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity.addRole(Role.USER);

            // 관리자
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

        @DisplayName("실패 - 이미 해지된 통장")
        @Test
        void failure_alreadyDeletedPassbook() {
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

        @DisplayName("실패 - 권한이 없는 일반 사용자")
        @Test
        void failure_anAuthorizedUser() {
            // given
            // 해당 통장의 원래 소유주인 사용자
            UserEntity userEntity1 = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity1.addRole(Role.USER);

            // 해당 통장의 소유주가 아니며 관리자도 아닌 사용자
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

    @DisplayName("잔액 조회")
    @Nested
    class GetBalanceTest {
        @DisplayName("성공 - 통장 소유주에 의한 조회")
        @Test
        void success_byOwner() {
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

        @DisplayName("성공 - 관리자에 의한 조회")
        @Test
        void success_byAdmin() {
            // given
            // 통장의 원래 소유주
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity.addRole(Role.USER);

            // 관리자
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

        @DisplayName("실패 - 해지된 통장에 대한 조회")
        @Test
        void failure_alreadyDeletedPassbook() {
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

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 조회")
        @Test
        void failure_anAuthorizedUser() {
            // given
            // 해당 통장의 원래 소유주인 사용자
            UserEntity userEntity1 = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity1.addRole(Role.USER);

            // 해당 통장의 소유주가 아니며 관리자도 아닌 사용자
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

    @DisplayName("통장 상세 조회")
    @Nested
    class GetPassbookTest {
        @DisplayName("성공 - 통장 소유주에 의한 상세 조회")
        @Test
        void success_byOwner() {
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

        @DisplayName("성공 - 관리자에 의한 상세 조회")
        @Test
        void success_byAdmin() {
            // given
            // 통장의 원래 소유주
            UserEntity userEntity = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity.addRole(Role.USER);

            // 관리자
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

        @DisplayName("실패 - 해지된 통장에 대한 상세 조회")
        @Test
        void failure_alreadyDeletedPassbook() {
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

        @DisplayName("실패 - 권한이 없는 일반 사용자에 의한 조회")
        @Test
        void failure_anAuthorizedUser() {
            // given
            // 해당 통장의 원래 소유주인 사용자
            UserEntity userEntity1 = UserEntity.builder()
                    .id(11L)
                    .build();
            userEntity1.addRole(Role.USER);

            // 해당 통장의 소유주가 아니며 관리자도 아닌 사용자
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

    @DisplayName("통장 목록 조회")
    @Nested
    class GetPassbooksTest {
        @DisplayName("성공")
        @Test
        void success_byOwner() throws Exception{
            // given
            UserEntity userEntity = UserEntity.builder()
                    .username("user1")
                    .build();

            BankEntity bankEntity = BankEntity.builder()
                    .name("은행 A")
                    .build();

            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .name("상품 A")
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

    @DisplayName("통장 비밀번호 변경")
    @Nested
    class UpdatePasswordTest {
        @DisplayName("성공 - 통장 소유주에 의한 변경")
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

        @DisplayName("성공 - 관리자에 의한 통장 비밀번호 변경")
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

        @DisplayName("실패 - 해지된 통장에 대한 변경")
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

        @DisplayName("실패 - 권한 없는 일반 사용자에 의한 변경")
        @Test
        void failure_anAuthorized() throws Exception {
            // 통장 소유주
            UserEntity userEntity1 = UserEntity.builder()
                    .username("user1")
                    .build();
            userEntity1.addRole(Role.USER);

            // 통장 소유주도 아니며 관리자도 아닌 사용자
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
}