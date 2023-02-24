package com.finance.onlinebanking.domain.product.service;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.bank.repository.BankRepository;
import com.finance.onlinebanking.domain.product.dto.PassbookProductRequestDto;
import com.finance.onlinebanking.domain.product.dto.PassbookProductResponseDto;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.product.repository.PassbookProductRepository;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private PassbookProductRepository passbookProductRepository;

    @DisplayName("상품 생성")
    @Nested
    class CreateProductTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            PassbookProductRequestDto passbookProductRequestDto = PassbookProductRequestDto.builder()
                    .name("테스트 통장 상품 이름")
                    .content("테스트 통장 상품 내용")
                    .term(365)
                    .build();
            BankEntity bankEntity = BankEntity.builder()
                    .name("A은행")
                    .code("001")
                    .build();
            given(bankRepository.findById(anyLong()))
                    .willReturn(Optional.of(bankEntity));

            // when
            PassbookProductResponseDto passbookProductResponseDto = productService.createProduct(3L, passbookProductRequestDto);

            // then
            assertEquals(passbookProductRequestDto.getTerm(), passbookProductResponseDto.getTerm());
            assertEquals(passbookProductRequestDto.getContent(), passbookProductResponseDto.getContent());
            verify(passbookProductRepository, times(1)).save(any(PassbookProductEntity.class));
        }

        @DisplayName("실패 - 존재하지 않는 은행 ID")
        @Test
        void failure_nonExistentBankId() throws Exception {
            // given
            PassbookProductRequestDto passbookProductRequestDto = PassbookProductRequestDto.builder()
                    .name("테스트 통장 상품 이름")
                    .content("테스트 통장 상품 내용")
                    .term(365)
                    .build();
            given(bankRepository.findById(anyLong()))
                    .willReturn(Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class,
                    () -> productService.createProduct(3L, passbookProductRequestDto));
        }
    }

    @DisplayName("상품 조회")
    @Nested
    class GetProductTest {
        @DisplayName("성공")
        @Test
        void success() throws Exception {
            // given
            BankEntity bankEntity = BankEntity.builder()
                    .name("A은행")
                    .code("001")
                    .build();
            PassbookProductEntity passbookProductEntity = PassbookProductEntity.builder()
                    .bank(bankEntity)
                    .term(365)
                    .amount(30000L)
                    .build();
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.of(passbookProductEntity));

            // when
            PassbookProductResponseDto passbookProductResponseDto = productService.getProduct(3L);

            // then
            assertEquals(passbookProductEntity.getTerm(), passbookProductResponseDto.getTerm());
            assertEquals(passbookProductEntity.getBank().getId(), passbookProductResponseDto.getBankId());
            assertEquals(passbookProductEntity.getAmount(), passbookProductResponseDto.getAmount());
        }

        @DisplayName("실패 - 존재하지 않는 상품 ID")
        @Test
        void failure_nonExistentProductId() throws Exception {
            given(passbookProductRepository.findByIdAndIsDeletedFalse(anyLong()))
                    .willReturn(Optional.empty());

            // when
            // then
            assertThrows(NonExistentException.class,
                    () -> productService.getProduct(3L));
        }
    }
}