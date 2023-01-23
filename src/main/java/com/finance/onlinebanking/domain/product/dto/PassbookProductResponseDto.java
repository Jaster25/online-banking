package com.finance.onlinebanking.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PassbookProductResponseDto {

    private Long id;

    private Long bankId;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endedAt;

    private BigDecimal interestRate;

    private String benefit;

    private String content;

    private String condition;

    private int term;

    private Long amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expiredAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;


    @Builder
    public PassbookProductResponseDto(Long id, Long bankId, String name, LocalDateTime startedAt, LocalDateTime endedAt, BigDecimal interestRate, String benefit, String content, String condition, int term, Long amount, LocalDateTime expiredAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.bankId = bankId;
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.interestRate = interestRate;
        this.benefit = benefit;
        this.content = content;
        this.condition = condition;
        this.term = term;
        this.amount = amount;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
