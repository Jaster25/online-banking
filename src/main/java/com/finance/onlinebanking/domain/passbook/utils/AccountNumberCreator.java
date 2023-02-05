package com.finance.onlinebanking.domain.passbook.utils;

import com.finance.onlinebanking.domain.passbook.repository.PassbookRepository;
import com.finance.onlinebanking.global.exception.ErrorCode;
import com.finance.onlinebanking.global.exception.custom.DuplicatedValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

public class AccountNumberCreator {
    /**
     * TODO: 계좌번호 생성 규칙 추가 예정
     */

//    public String createAccountNumber(Long bankId, String code) {
//        for (int i = 0; i < 10; i++) {
//            Random random = new Random();
//            String accountNumber = bankId.toString() + "-" + code + "-" + Integer.toString(random.nextInt());
//
//            if (!isDuplicated(accountNumber)) {
//                return accountNumber;
//            }
//        }
//        throw new DuplicatedValueException(ErrorCode.DUPLICATED_PASSBOOK_ACCOUNT_NUMBER);
//    }
//
//    public Boolean isDuplicated(String accountNumber) {
//        return passbookRepository.existsByAccountNumber(accountNumber);
//    }
}
