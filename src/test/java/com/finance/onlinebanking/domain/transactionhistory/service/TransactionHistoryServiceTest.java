package com.finance.onlinebanking.domain.transactionhistory.service;

import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.bank.service.BankService;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryRequestDto;
import com.finance.onlinebanking.domain.transactionhistory.dto.TransactionHistoryResponseDto;
import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import com.finance.onlinebanking.domain.transactionhistory.repository.TransactionHistoryRepository;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import com.finance.onlinebanking.global.exception.custom.UnAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionHistoryServiceTest {

    @InjectMocks
    private TransactionHistoryService transactionHistoryService;

    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    private UserEntity otherUser;

    private UserEntity depositPassbookOwner;

    private UserEntity withdrawPassbookOwner;

    private DepositWithdrawEntity withdrawPassbook;

    private DepositWithdrawEntity depositPassbook;

    private TransactionHistoryEntity transactionHistoryEntity;

    @BeforeEach
    public void setup() {
        otherUser = UserEntity.builder()
                .username("김도둑")
                .build();
        depositPassbookOwner = UserEntity.builder()
                .username("예금자")
                .build();
        withdrawPassbookOwner = UserEntity.builder()
                .username("출금자")
                .build();
        withdrawPassbook = DepositWithdrawEntity.builder()
                .user(withdrawPassbookOwner)
                .transferLimit(1000000L)
                .accountNumber("5-103-30449224")
                .balance(50000L)
                .build();
        depositPassbook = DepositWithdrawEntity.builder()
                .user(depositPassbookOwner)
                .transferLimit(100000L)
                .accountNumber("3-200-56001430")
                .balance(50000L)
                .build();
        transactionHistoryEntity = TransactionHistoryEntity.builder()
                .withdrawAccountNumber("5-103-30449224")
                .withdrawPassbook(withdrawPassbook)
                .depositAccountNumber("3-200-56001430")
                .depositPassbook(depositPassbook)
                .amount(3000L)
                .memo("거래내역 조회 유닛 테스트 메모")
                .commission(500L)
                .build();
    }

    @DisplayName("거래내역 생성")
    @Nested
    class CreateTransactionHistoryTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            TransactionHistoryRequestDto transactionHistoryRequestDto = TransactionHistoryRequestDto.builder()
                    .withdrawAccountNumber("5-103-30449224")
                    .depositAccountNumber("3-200-56001430")
                    .amount(3000L)
                    .memo("거래내역 생성 유닛 테스트 메모")
                    .commission(500L)
                    .build();

            // when
            TransactionHistoryResponseDto transactionHistoryResponseDto = transactionHistoryService.createTransactionHistory(transactionHistoryRequestDto, withdrawPassbook, depositPassbook);

            // then
            assertEquals(transactionHistoryRequestDto.getAmount(), transactionHistoryResponseDto.getAmount());
            assertEquals(transactionHistoryRequestDto.getMemo(), transactionHistoryResponseDto.getMemo());
            assertEquals(transactionHistoryRequestDto.getCommission(), transactionHistoryResponseDto.getCommission());
            assertEquals(transactionHistoryRequestDto.getDepositAccountNumber(), transactionHistoryResponseDto.getDepositAccountNumber());
            assertEquals(transactionHistoryRequestDto.getWithdrawAccountNumber(), transactionHistoryResponseDto.getWithdrawAccountNumber());
            verify(transactionHistoryRepository, times(1)).save(any(TransactionHistoryEntity.class));
        }
    }

    @DisplayName("거래내역 상세 조회")
    @Nested
    class GetTransactionHistoryTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            given(transactionHistoryRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(transactionHistoryEntity));

            // when
            TransactionHistoryResponseDto transactionHistoryResponseDto = transactionHistoryService.getTransactionHistory(withdrawPassbookOwner, 5L);

            // then
            assertEquals(transactionHistoryEntity.getAmount(), transactionHistoryResponseDto.getAmount());
            assertEquals(transactionHistoryEntity.getMemo(), transactionHistoryResponseDto.getMemo());
            assertEquals(transactionHistoryEntity.getCommission(), transactionHistoryResponseDto.getCommission());
            assertEquals(depositPassbook.getAccountNumber(), transactionHistoryResponseDto.getDepositAccountNumber());
            assertEquals(withdrawPassbook.getAccountNumber(), transactionHistoryResponseDto.getWithdrawAccountNumber());
        }

        @DisplayName("실패 - 권한없는 사용자")
        @Test
        void failure_otherUser() throws Exception {
            // given
            given(transactionHistoryRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(transactionHistoryEntity));

            // when
            // then
            assertThrows(UnAuthorizedException.class,
                    () -> transactionHistoryService.getTransactionHistory(otherUser, 3L));
        }

        @DisplayName("실패 - 존재하지 않는 거래내역 ID")
        @Test
        void failure_nonExistentTransactionId() throws Exception {
            // given
            given(transactionHistoryRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class,
                    () -> transactionHistoryService.getTransactionHistory(depositPassbookOwner, 3L));
        }
    }
}