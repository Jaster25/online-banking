package com.finance.onlinebanking.domain.passbook.entity.installment;

import com.finance.onlinebanking.domain.passbook.entity.PassBookEntity;
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
@Table(name = "installment")
@DiscriminatorValue("I")
@PrimaryKeyJoinColumn(name = "installment_id")
@SuperBuilder
@NoArgsConstructor

// 적금 통장
public class InstallmentEntity extends PassBookEntity {
    private LocalDateTime expiredAt;
}
