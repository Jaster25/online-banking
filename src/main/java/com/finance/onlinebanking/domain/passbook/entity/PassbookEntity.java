package com.finance.onlinebanking.domain.passbook.entity;

import com.finance.onlinebanking.domain.bank.entity.BankEntity;
import com.finance.onlinebanking.domain.passbook.utils.PassbookType;
import com.finance.onlinebanking.domain.product.entity.PassbookProductEntity;
import com.finance.onlinebanking.domain.transactionhistory.entity.TransactionHistoryEntity;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.global.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "passbook")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class PassbookEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passbook_id")
    private Long id;

    private String accountNumber;

    private String password;

    private Long balance;

    private BigDecimal interestRate;

    @Column(insertable = false, updatable = false)
    private String dtype;

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
    private List<TransactionHistoryEntity> withdrawTransactionHistories = new ArrayList<>();

    @OneToMany(mappedBy = "depositPassbook")
    private List<TransactionHistoryEntity> depositTransactionHistories = new ArrayList<>();


    public void updatePassword(String password) {
        this.password = password;
        this.updatedAt = LocalDateTime.now();
    }

    public void transfer(PassbookEntity depositPassbook, Long amount) {
        this.balance -= amount;
        depositPassbook.balance += amount;
    }

    public boolean isDepositWithdrawPassbook() {
        return dtype.equals(PassbookType.DW.toString());
    }

    public boolean isFixedDepositPassbook() {
        return dtype.equals(PassbookType.FD.toString());
    }

    public boolean isRegularInstallmentPassbook() {
        return dtype.equals(PassbookType.RI.toString());
    }

    public boolean isInstallmentPassbook() {
        return dtype.equals(PassbookType.I.toString());
    }

    public boolean isFreeInstallmentPassbook() {
        return dtype.equals(PassbookType.FI.toString());
    }

    public boolean checkBalance(Long amount) {
        return this.balance - amount < 0 ? false : true;
    }

    public boolean checkTransferLimit(Long amount) {
        return ((DepositWithdrawEntity) this).getTransferLimit() < amount ? false : true;
    }

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
