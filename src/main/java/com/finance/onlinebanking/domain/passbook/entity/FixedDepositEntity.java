package com.finance.onlinebanking.domain.passbook.entity;

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
@Table(name = "fixed_deposit")
@DiscriminatorValue("FD")
@PrimaryKeyJoinColumn(name = "fixed_deposit_id")
@SuperBuilder
@NoArgsConstructor

//예금 통장
public class FixedDepositEntity extends PassBookEntity {

    private LocalDateTime expiredAt;
}
