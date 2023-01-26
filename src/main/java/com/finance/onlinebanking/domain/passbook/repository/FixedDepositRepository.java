package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.FixedDepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedDepositRepository extends JpaRepository<FixedDepositEntity, Long> {
}
