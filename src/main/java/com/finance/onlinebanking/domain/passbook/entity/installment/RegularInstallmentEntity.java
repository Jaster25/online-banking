package com.finance.onlinebanking.domain.passbook.entity.installment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "regular_installment")
@PrimaryKeyJoinColumn(name = "regular_installment_id")
@DiscriminatorValue("RI")
public class RegularInstallmentEntity extends InstallmentEntity {

    private int depositDate;
    private Long amount;


    // -- 비즈니스 로직 -- //
    public void updateDepositInformation(LocalDateTime depositDate, Long amount) {
    }
}
