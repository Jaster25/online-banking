package com.finance.onlinebanking.domain.passbook.utils;

import java.util.Random;

public class AccountNumberCreator {
    /**
     * TODO: 계좌번호 생성 규칙 추가 예정
     */
    public String createAccountNumber(Long bankName, String code) {
        Random random = new Random();
        return bankName.toString() + "-" + code + "-" + Integer.toString(random.nextInt());
    }
}
