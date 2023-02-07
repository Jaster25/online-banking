package com.finance.onlinebanking.domain.product.entity;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "passbook_product")
@PrimaryKeyJoinColumn(name = "passbook_product_id")
@DiscriminatorValue("PP")
public class PassbookProductEntity extends ProductEntity{

    @OneToMany(mappedBy = "passbookProduct")
    private List<PassbookEntity> passbooks = new ArrayList<>();

    private int term;

    private Long amount;
}
