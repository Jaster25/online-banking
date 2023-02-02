package com.finance.onlinebanking.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductsResponseDto {

    private final List<PassbookProductResponseDto> passbookProducts;


    @Builder
    public ProductsResponseDto(List<PassbookProductResponseDto> passbookProducts) {
        this.passbookProducts = passbookProducts;
    }
}
