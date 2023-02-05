package com.finance.onlinebanking.domain.bank.repository;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<BankEntity, Long> {

    Optional<BankEntity> findByIdAndIsDeletedFalse(Long id);
}
