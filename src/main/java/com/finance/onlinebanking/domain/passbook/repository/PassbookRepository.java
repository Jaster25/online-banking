package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassbookRepository extends JpaRepository<PassbookEntity, Long> {

    List<PassbookEntity> findAllByUser(UserEntity user);
}
