package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositWithdrawRepository extends JpaRepository<DepositWithdrawEntity, Long> {
}
