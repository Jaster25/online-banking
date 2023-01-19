package com.finance.onlinebanking.domain.product.entity;

import com.finance.onlinebanking.domain.passbook.entity.PassBookEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "passbook_product")
@DiscriminatorValue("PP")
@PrimaryKeyJoinColumn(name = "passbook_product_id")
@SuperBuilder
@NoArgsConstructor

public class PassBookProductEntity extends ProductEntity{

    @OneToMany(mappedBy = "passBookProduct")
    private List<PassBookEntity> passbooks = new ArrayList<PassBookEntity>();

    private int term;

    private Long amount;

    private LocalDateTime expiredAt;
}
