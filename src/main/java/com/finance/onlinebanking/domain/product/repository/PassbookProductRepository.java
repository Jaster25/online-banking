package com.finance.onlinebanking.domain.product.repository;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PassbookProductRepository extends JpaRepository<PassbookProductEntity, Long> {

    List<PassbookProductEntity> findAllByBank(BankEntity bank);

    Optional<PassbookProductEntity>  findByIdAndIsDeletedFalse(Long id);
}
