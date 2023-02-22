package com.finance.onlinebanking.domain.bank.service;

import com.finance.onlinebanking.domain.bank.dto.BankRequestDto;
import com.finance.onlinebanking.domain.bank.dto.BankResponseDto;
import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.product.dto.ProductsResponseDto;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    @InjectMocks
    private BankService bankService;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private PassbookProductRepository passbookProductRepository;

    @DisplayName("은행 생성")
    @Nested
    class CreateBankTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            BankRequestDto bankRequestDto = BankRequestDto.builder()
                    .name("A은행")
                    .code("001")
                    .branch("오목교")
                    .build();

            // when
            BankResponseDto bankResponseDto = bankService.createBank(bankRequestDto);

            // then
            assertEquals(bankRequestDto.getName(), bankResponseDto.getName());
            assertEquals(bankRequestDto.getCode(), bankResponseDto.getCode());
            assertEquals(bankRequestDto.getBranch(), bankResponseDto.getBranch());
            verify(bankRepository, times(1)).save(any(BankEntity.class));
        }
    }

    @DisplayName("은행 조회")
    @Nested
    class GetBankTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            BankEntity bankEntity = BankEntity.builder()
                    .id(1L)
                    .name("A은행")
                    .code("001")
                    .branch("오목교")
                    .build();
            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));

            // when
            BankResponseDto bankResponseDto = bankService.getBank(1L);

            // then
            assertEquals(bankEntity.getName(), bankResponseDto.getName());
            assertEquals(bankEntity.getCode(), bankResponseDto.getCode());
            assertEquals(bankEntity.getBranch(), bankResponseDto.getBranch());
            verify(bankRepository, times(1)).findByIdAndIsDeletedFalse(anyLong());
        }
        
        @DisplayName("실패 - 존재하지 않는 은행 ID")
        @Test
        void failure_nonExistentBankId() throws Exception {
            // given
            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class,
                    () -> bankService.getBank(1L));
        }
    }

    @DisplayName("특정 은행 상품 목록 조회")
    @Nested
    class GetProductsTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            BankEntity bankEntity = BankEntity.builder()
                    .name("A은행")
                    .code("001")
                    .branch("오목교")
                    .build();
            PassbookProductEntity passbookProductEntity1 = PassbookProductEntity.builder()
                    .bank(bankEntity)
                    .term(365)
                    .build();
            PassbookProductEntity passbookProductEntity2 = PassbookProductEntity.builder()
                    .bank(bankEntity)
                    .term(730)
                    .build();
            PassbookProductEntity passbookProductEntity3 = PassbookProductEntity.builder()
                    .bank(bankEntity)
                    .term(1460)
                    .build();
            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(bankEntity));
            given(passbookProductRepository.findAllByBankAndIsDeletedFalse(any(BankEntity.class)))
                    .willReturn(List.of(passbookProductEntity1, passbookProductEntity2, passbookProductEntity3));

            // when
            ProductsResponseDto productsResponseDto = bankService.getProducts(1L);

            // then
            assertEquals(3, productsResponseDto.getPassbookProducts().size());
        }

        @DisplayName("실패 - 존재하지 않는 은행 ID")
        @Test
        void failure_nonExistentBankId() throws Exception {
            // given
            given(bankRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class,
                    () -> bankService.getProducts(3L));
        }
    }
}