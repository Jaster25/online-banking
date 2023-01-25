package com.finance.onlinebanking.domain.passbook.utils;

public class AccountNumberCreator {
    /**
     * TODO: 계좌번호 생성 규칙 추가 예정
     */
    public String createAccountNumber(Long bankName, String code) {
        return bankName.toString() + "-" + code;
    }
}
