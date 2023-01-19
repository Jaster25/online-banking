package com.finance.onlinebanking.domain.passbook.entity.installment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "free_installment")
@DiscriminatorValue("FI")
@SuperBuilder
@NoArgsConstructor

// 자유 적금 통장
public class FreeInstallmentEntity extends InstallmentEntity {

}
