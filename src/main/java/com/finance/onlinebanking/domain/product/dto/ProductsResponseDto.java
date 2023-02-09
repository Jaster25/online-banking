package com.finance.onlinebanking.domain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductsResponseDto {

    @Schema(description = "통장 상품 목록")
    private final List<PassbookProductResponseDto> passbookProducts;


    @Builder
    public ProductsResponseDto(List<PassbookProductResponseDto> passbookProducts) {
        this.passbookProducts = passbookProducts;
    }
}
