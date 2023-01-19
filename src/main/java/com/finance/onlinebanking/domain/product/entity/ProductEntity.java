package com.finance.onlinebanking.domain.product.entity;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.global.common.BaseTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@SuperBuilder
@NoArgsConstructor
@Table(name = "product")

public abstract class ProductEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bank_id")
    private BankEntity bank;

    private String name;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private BigDecimal interestRate;

    private String benefit;

    private String content;

    private String condition;
}
