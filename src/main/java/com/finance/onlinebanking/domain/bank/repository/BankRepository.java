package com.finance.onlinebanking.domain.bank.repository;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankEntity, Long> {
}
