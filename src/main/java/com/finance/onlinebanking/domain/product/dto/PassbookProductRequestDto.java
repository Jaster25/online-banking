package com.finance.onlinebanking.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotBlank(message = "NOT_NULL_PASSBOOK_PRODUCT_NAME")
    private String name;

    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_STARTED_AT")
    @Future(message = "INVALID_PASSBOOK_PRODUCT_STARTED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startedAt;

    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_ENDED_AT")
    @Future(message = "INVALID_PASSBOOK_PRODUCT_ENDED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endedAt;

    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_INTEREST_RATE")
    @Positive(message = "INVALID_PASSBOOK_PRODUCT_INTEREST_RATE")
    private BigDecimal interestRate;

    @NotBlank(message = "NOT_NULL_PASSBOOK_PRODUCT_BENEFIT")
    private String benefit;

    @NotBlank(message = "NOT_NULL_PASSBOOK_PRODUCT_CONTENT")
    private String content;

    @NotBlank(message = "NOT_NULL_PASSBOOK_PRODUCT_CONDITIONS")
    private String conditions;

    @NotNull(message = "NOT_NULL_PASSBOOK_PRODUCT_TERM")
    @Positive(message = "INVALID_PASSBOOK_PRODUCT_TERM")
    private int term;

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
