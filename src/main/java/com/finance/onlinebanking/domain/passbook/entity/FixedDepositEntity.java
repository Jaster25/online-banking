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
@SuperBuilder
@NoArgsConstructor
@Table(name = "fixed_deposit")
@PrimaryKeyJoinColumn(name = "fixed_deposit_id")
@DiscriminatorValue("FD")
public class FixedDepositEntity extends PassbookEntity {

    private LocalDateTime expiredAt;
}
