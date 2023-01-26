package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassbookRepository extends JpaRepository<PassbookEntity, Long> {

}
