package com.finance.onlinebanking.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PassbookProductResponseDto {

    private final Long id;

    private final Long bankId;

    private final String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endedAt;

    private final BigDecimal interestRate;

    private final String benefit;

    private final String content;

    private final String conditions;

    private final int term;

    private final Long amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime expiredAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime updatedAt;


    @Builder
    private PassbookProductResponseDto(Long id, Long bankId, String name, LocalDateTime startedAt, LocalDateTime endedAt, BigDecimal interestRate, String benefit, String content, String conditions, int term, Long amount, LocalDateTime expiredAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.bankId = bankId;
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.interestRate = interestRate;
        this.benefit = benefit;
        this.content = content;
        this.conditions = conditions;
        this.term = term;
        this.amount = amount;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static PassbookProductResponseDto of(PassbookProductEntity passbookProductEntity) {
        return PassbookProductResponseDto.builder()
                .id(passbookProductEntity.getId())
                .bankId(passbookProductEntity.getBank().getId())
                .name(passbookProductEntity.getName())
                .startedAt(passbookProductEntity.getStartedAt())
                .endedAt(passbookProductEntity.getEndedAt())
                .interestRate(passbookProductEntity.getInterestRate())
                .benefit(passbookProductEntity.getBenefit())
                .content(passbookProductEntity.getContent())
                .conditions(passbookProductEntity.getConditions())
                .term(passbookProductEntity.getTerm())
                .amount(passbookProductEntity.getAmount())
                .expiredAt(passbookProductEntity.getExpiredAt())
                .createdAt(passbookProductEntity.getCreatedAt())
                .updatedAt(passbookProductEntity.getUpdatedAt())
                .build();
    }
}
