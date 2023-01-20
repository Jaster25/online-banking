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


    @Builder
    public UserEntity(Long id, List<PassbookEntity> passbooks, String name, String username, String password, String role) {
        this.id = id;
        this.passbooks = passbooks;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public void updatePassword(String password) {
        this.password = password;
        updatedAt = LocalDateTime.now();
    }
}
