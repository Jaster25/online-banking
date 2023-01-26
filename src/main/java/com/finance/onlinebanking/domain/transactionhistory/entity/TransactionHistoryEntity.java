package com.finance.onlinebanking.domain.transactionhistory.entity;

import com.finance.onlinebanking.domain.passbook.entity.PassbookEntity;
import com.finance.onlinebanking.global.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "transaction_history")
public class TransactionHistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "transaction_history_id")
    private Long id;

    private String withdrawAccountNumber;

    private String depositAccountNumber;

    private Long amount;

    private String memo;

    private Long commission;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "withdraw_passbook_id")
    private PassbookEntity withdrawPassbook;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "deposit_passbook_id")
    private PassbookEntity depositPassbook;


    // 연관관계 메서드
    public void setWithdrawPassbook(PassbookEntity withdrawPassbook) {
        if (this.withdrawPassbook != null) {
            this.withdrawPassbook.getWithdrawTransactionHistories().remove(this);
        }
        this.withdrawPassbook = withdrawPassbook;
        withdrawPassbook.getWithdrawTransactionHistories().add(this);
    }

    public void setDepositPassbook(PassbookEntity depositPassbook) {
        if (this.depositPassbook != null) {
            this.depositPassbook.getDepositTransactionHistories().remove(this);
        }
        this.depositPassbook = depositPassbook;
        depositPassbook.getDepositTransactionHistories().add(this);
    }
}
