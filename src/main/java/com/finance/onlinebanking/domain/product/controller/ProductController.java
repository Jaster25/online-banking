package com.finance.onlinebanking.domain.product.controller;

import com.finance.onlinebanking.domain.product.dto.PassbookProductRequestDto;
import com.finance.onlinebanking.domain.product.dto.PassbookProductResponseDto;
import com.finance.onlinebanking.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;


    @PostMapping("/{bankId}")
    public ResponseEntity<PassbookProductResponseDto> createProductApi(@PathVariable("bankId") String bankId, @RequestBody PassbookProductRequestDto passbookProductRequestDto) {
        PassbookProductResponseDto passbookProductResponseDto = productService.createProduct(Long.parseLong(bankId), passbookProductRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookProductResponseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<PassbookProductResponseDto> getProductApi(@PathVariable("productId") String productId) {
        PassbookProductResponseDto passbookProductResponseDto = productService.getProduct(Long.parseLong(productId));
        return ResponseEntity.status(HttpStatus.OK).body(passbookProductResponseDto);
    }
}
