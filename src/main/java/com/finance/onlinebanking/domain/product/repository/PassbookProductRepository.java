package com.finance.onlinebanking.domain.product.repository;

import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassbookProductRepository extends JpaRepository<PassbookProductEntity, Long> {
}
