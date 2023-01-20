package com.finance.onlinebanking.domain.user.enitty;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.global.common.BaseTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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


    // -- 비즈니스 로직 -- //
    public void updatePassword(String password) {

    }

}
