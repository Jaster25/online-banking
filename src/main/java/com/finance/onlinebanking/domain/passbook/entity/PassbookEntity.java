package com.finance.onlinebanking.domain.passbook.entity;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.global.common.BaseTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "passbook")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class PassbookEntity extends BaseTime {

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
    private PassbookProductEntity passbookProduct;

    @OneToMany(mappedBy = "withdrawPassbook")
    List<TransactionHistoryEntity> withdrawPassbook;

    @OneToMany(mappedBy = "depositPassbook")
    List<TransactionHistoryEntity> depositPassbook;

    private String accountNumber;

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

    public void setPassbookProduct(PassbookProductEntity passbookProduct) {
        if (this.passbookProduct != null) {
            this.passbookProduct.getPassbooks().remove(this);
        }
        this.passbookProduct = passbookProduct;
        passbookProduct.getPassbooks().add(this);
    }
}
