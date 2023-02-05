package com.finance.onlinebanking.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     * G: Global
     */
    INVALID_REQUEST("G001", "잘못된 요청입니다."),
    NONEXISTENT_AUTHENTICATION("G002", "로그인이 필요합니다."),
    NONEXISTENT_AUTHORIZATION("G003", "권한이 없습니다."),

    /**
     * U: User
     */
    NONEXISTENT_USER("U001", "존재하지 않는 사용자입니다."),
    // 유효성 확인
    // 아이디
    NOT_NULL_USER_ID("U101", "아이디는 필수입니다."),
    INVALID_USER_ID("U102", "아이디는 영문 대소문자와 숫자 4자 ~ 10자로 이루어져야 합니다."),
    // 비밀번호
    NOT_NULL_USER_PASSWORD("U111", "비밀번호는 필수입니다."),
    INVALID_USER_PASSWORD("U112", "비밀번호는 영문 대소문자, 숫자, 특수기호가 적어도 1개 이상씩 포함된 4자 ~ 20자로 이루어져야 합니다."),
    // 이름
    NOT_NULL_USER_NAME("U121", "이름은 필수입니다."),
    // 삭제
    ALREADY_DELETED_USER("U201", "이미 삭제된 사용자입니다."),
    // 회원 가입
    DUPLICATED_USER_ID("U301", "이미 존재하는 사용자 아이디입니다."),
    // 로그인
    LOGIN_FAILURE("U401", "아이디 또는 비밀번호가 잘못 입력되었습니다."),

    /**
     * P: Passbook
     */
    NONEXISTENT_PASSBOOK("P001", "존재하지 않는 통장입니다."),
    NONEXISTENT_DW_PASSBOOK("P002", "존재하지 않는 입출금 통장입니다."),
    NONEXISTENT_FD_PASSBOOK("P003", "존재하지 않는 예금 통장입니다."),
    NONEXISTENT_FI_PASSBOOK("P004", "존재하지 않는 자유 적금 통장입니다."),
    NONEXISTENT_RI_PASSBOOK("P005", "존재하지 않는 정기 적금 통장입니다."),
    // 입금, 출금
    NONEXISTENT_WITHDRAW_PASSBOOK("P006", "존재하지 않는 출금 통장입니다."),
    NONEXISTENT_DEPOSIT_PASSBOOK("P007", "존재하지 않는 입금 통장입니다."),
    // 유효성 확인
    NOT_NULL_PASSBOOK_TYPE("P101", "통장 종류 필수입니다."),
    NOT_NULL_PASSBOOK_CONTENT("P102", "통장 내용은 필수입니다."),
    // 삭제
    ALREADY_DELETED_PASSBOOK("P201", "이미 삭제된 통장입니다."),

    /**
     * B: Bank
     */
    NONEXISTENT_BANK("B001", "존재하지 않는 은행입니다."),
    // 유효성 확인
    NOT_NULL_BANK_NAME("B101", "은행 이름은 필수입니다."),
    NOT_NULL_BANK_CODE("B102", "은행 코드는 필수입니다."),
    NOT_NULL_BANK_BRANCH("B103", "은행 지점은 필수입니다."),
    // 삭제
    ALREADY_DELETED_BANK("B201", "이미 삭제된 은행입니다."),

    /**
     * R: Product
     */
    NONEXISTENT_PRODUCT("R001", "존재하지 않는 상품입니다."),
    NONEXISTENT_PASSBOOK_PRODUCT("R002", "존재하지 않는 통장 상품입니다."),
    // 유효성 확인
    NOT_NULL_COMMENT_PRODUCT("R101", "상품 내용은 필수입니다."),

    /**
     * T: Transaction
     */
    NONEXISTENT_TRANSACTION("T001", "존재하지 않는 거래내역입니다."),
    // 삭제
    ALREADY_DELETED_TRANSACTION("T101", "이미 삭제된 거래내역입니다."),
    ;

    private final String code;
    private final String message;


    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
