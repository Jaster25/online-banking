package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.installment.FreeInstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeInstallmentRepository extends JpaRepository<FreeInstallmentEntity, Long> {

}
