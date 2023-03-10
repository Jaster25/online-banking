package com.finance.onlinebanking.global.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {

        @CreatedDate
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        protected LocalDateTime updatedAt;

        @Builder.Default
        @Column(name = "is_deleted")
        boolean isDeleted = false;


        public void delete() {
                this.isDeleted = true;
                updatedAt = LocalDateTime.now();
        }
}
