package com.finance.onlinebanking.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PassbookProductRequestDto {

    @Schema(description = "통장 상품 이름", defaultValue = "테스트 통장 상품")
    @NotBlank(message = "NOT_NULL_PASSBOOK_PRODUCT_NAME")
    private String name;

    @Schema(description = "시작일")
    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_STARTED_AT")
    @Future(message = "INVALID_PASSBOOK_PRODUCT_STARTED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startedAt;

    @Schema(description = "종료일")
    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_ENDED_AT")
    @Future(message = "INVALID_PASSBOOK_PRODUCT_ENDED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endedAt;

    @Schema(description = "금리", defaultValue = "1.0")
    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_INTEREST_RATE")
    @Positive(message = "INVALID_PASSBOOK_PRODUCT_INTEREST_RATE")
    private BigDecimal interestRate;

    @Schema(description = "혜택", defaultValue = "혜택은 ~~입니다.")
    @NotBlank(message = "NOT_NULL_PASSBOOK_PRODUCT_BENEFIT")
    private String benefit;

    @Schema(description = "내용", defaultValue = "통장 상품의 내용은 ~~입니다.")
    @NotBlank(message = "NOT_NULL_PASSBOOK_PRODUCT_CONTENT")
    private String content;

    @Schema(description = "조건", defaultValue = "가입 조건은 ~~입니다.")
    @NotBlank(message = "NOT_NULL_PASSBOOK_PRODUCT_CONDITIONS")
    private String conditions;

    @Schema(description = "기간", defaultValue = "365")
    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_TERM")
    @Positive(message = "INVALID_PASSBOOK_PRODUCT_TERM")
    private int term;

    @Schema(description = "금액", defaultValue = "100000")
    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_AMOUNT")
    @Positive(message = "INVALID_PASSBOOK_PRODUCT_AMOUNT")
    private Long amount;


    @Builder
    public PassbookProductRequestDto(String name, LocalDateTime startedAt, LocalDateTime endedAt, BigDecimal interestRate, String benefit, String content, String conditions, int term, Long amount) {
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.interestRate = interestRate;
        this.benefit = benefit;
        this.content = content;
        this.conditions = conditions;
        this.term = term;
        this.amount = amount;
    }
}
