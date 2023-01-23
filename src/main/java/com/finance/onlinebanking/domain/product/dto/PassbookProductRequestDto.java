package com.finance.onlinebanking.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PassbookProductRequestDto {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endedAt;

    private BigDecimal interestRate;

    private String benefit;

    private String content;

    private String condition;

    private int term;

    private Long amount;


    @Builder
    public PassbookProductRequestDto(String name, LocalDateTime startedAt, LocalDateTime endedAt, BigDecimal interestRate, String benefit, String content, String condition) {
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.interestRate = interestRate;
        this.benefit = benefit;
        this.content = content;
        this.condition = condition;
    }

    @Builder
    public PassbookProductRequestDto(String name, LocalDateTime startedAt, LocalDateTime endedAt, BigDecimal interestRate, String benefit, String content, String condition, int term, Long amount) {
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.interestRate = interestRate;
        this.benefit = benefit;
        this.content = content;
        this.condition = condition;
        this.term = term;
        this.amount = amount;
    }
}
