package com.finance.onlinebanking.domain.product.entity;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.global.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String conditions;


    // 연관관계 메서드
    public void setBank(BankEntity bank) {
        if (this.bank != null) {
            this.bank.getProducts().remove(this);
        }
        this.bank = bank;
        bank.getProducts().add(this);
    }
}
