package com.finance.onlinebanking.domain.bank.entity;

import com.finance.onlinebanking.domain.passbook.entity.PassBookEntity;
import com.finance.onlinebanking.domain.product.entity.ProductEntity;
import com.finance.onlinebanking.global.common.BaseTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "bank")
@SuperBuilder
@NoArgsConstructor

public class BankEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_id")
    private Long id;

    @OneToMany(mappedBy = "bank")
    private List<PassBookEntity> passbooks;

    @OneToMany(mappedBy = "bank")
    private List<ProductEntity> products;

    private String name;

    private String code;

    private String branch;
}
