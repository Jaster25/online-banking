package com.finance.onlinebanking.domain.passbook.repository;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface PassbookRepository extends JpaRepository<PassbookEntity, Long> {

    List<PassbookEntity> findAllByUser(UserEntity user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PassbookEntity p where p.id = :passbook_id")
    PassbookEntity findByIdForUpdate(@Param("passbook_id") Long passbook_id);
}
