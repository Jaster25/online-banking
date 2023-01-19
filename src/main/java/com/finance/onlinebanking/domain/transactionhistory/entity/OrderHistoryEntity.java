//package enittyTest.entitytest.entity.orderhistory;
//
//import enittyTest.entitytest.entity.bank.BankEntity;
//import enittyTest.entitytest.entity.passbook.PassBookEntity;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//
//import static jakarta.persistence.FetchType.*;
//
//@Entity
//@NoArgsConstructor
//@Getter
//@Table(name = "transaction_history")
//public class OrderHistoryEntity {
//    @Id
//    @GeneratedValue
//    @Column(name = "transaction_history_id")
//    private Long id;
//
//    // -------------------------------------------------- //
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "passbook_id")
//    private PassBookEntity withdrawPassBook;
//
////    @ManyToOne(fetch = LAZY)
////    @JoinColumn(name = "passbook_id")
////    private PassBookEntity depositPassBook; // 역시나 에러 발생
//    // -------------------------------------------------- //
//
//    private String withdrawAccountNumber;
//
//    private String depositAccountNumber;
//
//    private int amount;
//
//    private String memo;
//
//    private Long commission;
//
//
//    // 연관 관계 메서드
////    public void setWithdrawPassBook(PassBookEntity passBook) {
////        if(this.pass)
////
////    }
//}
