package com.finance.onlinebanking.domain.user.entity;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.global.common.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<PassbookEntity> passbooks;

    private String name;

    private String username;

    private String password;

    private String role;

    @Column(name = "is_deleted")
    private boolean isDeleted;


    @Builder
    public UserEntity(Long id, List<PassbookEntity> passbooks, String name, String username, String password, String role, boolean isDeleted) {
        this.id = id;
        this.passbooks = passbooks;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
    }


    public void updatePassword(String password) {
        this.password = password;
        updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.isDeleted = true;
        updatedAt = LocalDateTime.now();
    }
}
