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
@DiscriminatorValue("RI")
@Getter
@Table(name = "regular_installment")
@PrimaryKeyJoinColumn(name = "regular_installment_id")
@SuperBuilder
@NoArgsConstructor

// 정기 적금 통장
public class RegularInstallmentEntity extends InstallmentEntity {

    private LocalDateTime depositDate;
    private Long amount;


    // -- 비즈니스 로직 -- //
    public void updateDepositInformation(LocalDateTime depositDate, Long amount) {
    }
}
