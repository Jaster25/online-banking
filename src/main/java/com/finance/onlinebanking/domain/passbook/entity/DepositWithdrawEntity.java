package com.finance.onlinebanking.domain.passbook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "deposit_withdraw")
@PrimaryKeyJoinColumn(name = "deposit_withdraw_id")
@DiscriminatorValue("DW")
public class DepositWithdrawEntity extends PassbookEntity {

    private Long transferLimit;


    // -- 비즈니스 로직 -- //
    public void updateTransferLimit(Long transferLimit) {
    }
}
