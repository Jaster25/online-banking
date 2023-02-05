package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface PassbookRepository extends JpaRepository<PassbookEntity, Long> {

    List<PassbookEntity> findAllByUserAndIsDeletedFalse(UserEntity user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PassbookEntity p where p.id = :passbook_id")
    Optional<PassbookEntity> findByIdAndIsDeletedFalseForUpdate(@Param("passbook_id") Long passbook_id);

    Boolean existsByAccountNumber(String accountNumber);

    Optional<PassbookEntity> findByIdAndIsDeletedFalse(Long id);
}
