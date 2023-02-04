package com.finance.onlinebanking.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PassbookProductResponseDto {

    @Schema(description = "통장 상품 아이디")
    private final Long id;

    @Schema(description = "은행 아이디")
    private final Long bankId;

    @Schema(description = "상품 이름")
    private final String name;

    @Schema(description = "시작일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime startedAt;

    @Schema(description = "종료일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime endedAt;

    @Schema(description = "금리")
    private final BigDecimal interestRate;

    @Schema(description = "혜택")
    private final String benefit;

    @Schema(description = "내용")
    private final String content;

    @Schema(description = "조건")
    private final String conditions;

    @Schema(description = "기간")
    private final int term;

    @Schema(description = "금액")
    private final Long amount;

    @Schema(description = "만기일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime expiredAt;

    @Schema(description = "생성일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    @Schema(description = "수정일")
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
