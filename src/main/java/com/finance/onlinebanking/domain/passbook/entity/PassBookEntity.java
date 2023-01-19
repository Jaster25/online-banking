package com.finance.onlinebanking.domain.passbook.entity;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.product.entity.PassBookProductEntity;
import com.finance.onlinebanking.domain.user.enitty.UserEntity;
import com.finance.onlinebanking.global.common.BaseTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@SuperBuilder
@NoArgsConstructor
@Table(name = "passbook")


public abstract class PassBookEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "passbook_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bank_id")
    private BankEntity bank;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "passbook_product_id")
    private PassBookProductEntity passBookProduct;

    /**
     * 거래내역과 매핑 부분
     * 같은 필드 참조가 두갠데... 생각해보기
     */
//    @OneToMany(mappedBy = "")

    private String accountNumber; // 따로 암호화

    private String password;

    private Long balance;

    private BigDecimal interestRate;


    // 비즈니스 로직
    public void updatePassword(String password) {
    }

    public void transfer(String depositAccountNumber, Long amount, String memo) {

    }


    // 연관관계 메서드
    public void setUser(UserEntity user) {
        if (this.user != null) {
            this.user.getPassbooks().remove(this);
        }
        this.user = user;
        user.getPassbooks().add(this);
    }

    public void setBank(BankEntity bank) {
        if (this.bank != null) {
            this.bank.getPassbooks().remove(this);
        }
        this.bank = bank;
        bank.getPassbooks().add(this);
    }

    public void setPassBookProduct(PassBookProductEntity passBookProduct) {
        if (this.passBookProduct != null) {
            this.passBookProduct.getPassbooks().remove(this);
        }
        this.passBookProduct = passBookProduct;
        passBookProduct.getPassbooks().add(this);
    }
}
