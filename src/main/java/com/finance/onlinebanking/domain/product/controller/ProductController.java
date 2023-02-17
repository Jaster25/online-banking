package com.finance.onlinebanking.domain.product.controller;

import com.finance.onlinebanking.domain.passbook.dto.PassbooksResponseDto;
import com.finance.onlinebanking.domain.product.dto.PassbookProductRequestDto;
import com.finance.onlinebanking.domain.product.dto.PassbookProductResponseDto;
import com.finance.onlinebanking.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "상품 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "관리자가 은행에 상품을 등록한다.")
    @ApiResponse(responseCode = "201", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbookProductResponseDto.class)))
    @PostMapping("/{bankId}")
    public ResponseEntity<PassbookProductResponseDto> createProductApi(@Parameter(description = "은행 ID") @PathVariable("bankId") Long bankId,
                                                                       @Valid @RequestBody PassbookProductRequestDto passbookProductRequestDto) {
        PassbookProductResponseDto passbookProductResponseDto = productService.createProduct(bankId, passbookProductRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passbookProductResponseDto);
    }

    @Operation(summary = "상품 상세 조회", description = "사용자가 상품을 상세 조회한다.")
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = PassbookProductResponseDto.class)))
    @GetMapping("/{productId}")
    public ResponseEntity<PassbookProductResponseDto> getProductApi(@Parameter(description = "상품 ID") @PathVariable("productId") Long productId) {
        PassbookProductResponseDto passbookProductResponseDto = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(passbookProductResponseDto);
    }
}
