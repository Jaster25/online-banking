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
@Table(name = "deposit_withdraw")
@DiscriminatorValue("DW")
@PrimaryKeyJoinColumn(name = "deposit_withdraw_id")
@SuperBuilder
@NoArgsConstructor

// 입출금 통장
public class DepositWithdrawEntity extends PassBookEntity {
    private Long transferLimit;


    // -- 비즈니스 로직 -- //
    public void updateTransferLimit(Long transferLimit) {

    }
}
