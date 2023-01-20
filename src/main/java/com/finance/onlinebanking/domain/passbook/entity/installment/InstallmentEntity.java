package com.finance.onlinebanking.domain.passbook.entity.installment;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
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
@Table(name = "installment")
@PrimaryKeyJoinColumn(name = "installment_id")
@DiscriminatorValue("I")
public class InstallmentEntity extends PassbookEntity {

    private LocalDateTime expiredAt;
}
