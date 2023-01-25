package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.installment.RegularInstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularInstallmentRepository extends JpaRepository<RegularInstallmentEntity, Long> {
}
