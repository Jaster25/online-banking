package com.finance.onlinebanking.domain.bank.entity;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.product.entity.ProductEntity;
import com.finance.onlinebanking.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "bank")
public class BankEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long id;

    private String name;

    private String code;

    private String branch;

    @Builder.Default
    @OneToMany(mappedBy = "bank")
    private List<PassbookEntity> passbooks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "bank")
    private List<ProductEntity> products = new ArrayList<>();
}
