package com.finance.onlinebanking.domain.bank.entity;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.product.entity.ProductEntity;
import com.finance.onlinebanking.global.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "bank")
public class BankEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_id")
    private Long id;

    @OneToMany(mappedBy = "bank")
    private List<PassbookEntity> passbooks;

    @OneToMany(mappedBy = "bank")
    private List<ProductEntity> products;

    private String name;

    private String code;

    private String branch;
}
