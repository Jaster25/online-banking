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
    LACK_OF_WITHDRAW_PASSBOOK_BALANCE("P008", "출금 통장의 잔액이 부족합니다."),
    LACK_OF_WITHDRAW_PASSBOOK_TRANSFER_LIMIT("P009", "출금 통장의 이체 한도가 부족합니다."),
    // 유효성 확인
    NOT_NULL_PASSBOOK_TYPE("P101", "통장 종류 필수입니다."),
    NOT_NULL_PASSBOOK_CONTENT("P102", "통장 내용은 필수입니다."),
    NOT_NULL_PASSBOOK_BALANCE("P103", "잔액은 필수입니다."),
    NOT_NULL_PASSBOOK_INTEREST_RATE("P104", "금리는 필수입니다."),
    NOT_NULL_PASSBOOK_TRANSFER_LIMIT("P105", "이체한도는 필수입니다."),
    NOT_NULL_PASSBOOK_EXPIRED_AT("P106", "만기일은 필수입니다."),
    NOT_NULL_PASSBOOK_PASSWORD("P107", "비밀번호는 필수입니다."),
    NOT_NULL_PASSBOOK_DEPOSIT_DATE("P108", "입금 예정일은 필수입니다."),
    NOT_NULL_PASSBOOK_AMOUNT("P109", "금액은 필수입니다."),
    INVALID_PASSBOOK_BALANCE("P110", "잔액은 음수일 수 없습니다."),
    INVALID_PASSBOOK_INTEREST_RATE("P111", "금리는 양수여야 없습니다."),
    INVALID_PASSBOOK_TRANSFER_LIMIT("P112", "이체한도는 음수일 수 없습니다."),
    INVALID_PASSBOOK_EXPIRED_AT("P113","만기일은 과거일 수 없습니다."),
    INVALID_PASSBOOK_PASSWORD("P114", "통장 비밀번호는 숫자로만 이루어져야 합니다."),
    INVALID_PASSBOOK_DEPOSIT_DATE("P115", "입금 예정일은 과거일 수 없습니다."),
    INVALID_PASSBOOK_AMOUNT("P116", "금액은 양수여야 합니다."),
    INVALID_PASSBOOK_TYPE("P117", "유효하지 않은 통장 종류입니다."),
    // 중복
    DUPLICATED_PASSBOOK_ACCOUNT_NUMBER("P201", "중복된 계좌번호 입니다."),

    /**
     * B: Bank
     */
    NONEXISTENT_BANK("B001", "존재하지 않는 은행입니다."),
    // 유효성 확인
    NOT_NULL_BANK_NAME("B101", "은행 이름은 필수입니다."),
    NOT_NULL_BANK_CODE("B102", "은행 코드는 필수입니다."),
    NOT_NULL_BANK_BRANCH("B103", "은행 지점은 필수입니다."),

    /**
     * R: Product
     */
    NONEXISTENT_PRODUCT("R001", "존재하지 않는 상품입니다."),
    NONEXISTENT_PASSBOOK_PRODUCT("R002", "존재하지 않는 통장 상품입니다."),
    // 유효성 확인
    NOT_NULL_COMMENT_PRODUCT("R101", "상품 내용은 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_NAME("R102", "통장 상품 이름은 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_STARTED_AT("R103", "통장 상품 시작일은 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_ENDED_AT("R104", "통장 상품 종료일은 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_INTEREST_RATE("R105", "통장 상품 금리는 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_BENEFIT("R106", "통장 상품 혜택은 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_CONTENT("R107", "통장 상품 내용 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_CONDITIONS("R108", "통장 상품 조건은 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_TERM("R109", "통장 상품 기간은 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_AMOUNT("R110", "통장 상품 월 납입금은 필수입니다."),
    NOT_NULL_PASSBOOK_PRODUCT_EXPIRED_AT("R111", "통장 상품 만기일은 필수입니다."),
    INVALID_PASSBOOK_PRODUCT_STARTED_AT("R112", "통장 상품 시작일이 유효하지 않습니다."),
    INVALID_PASSBOOK_PRODUCT_ENDED_AT("R113", "통장 상품 종료일이 유효하지 않습니다."),
    INVALID_PASSBOOK_PRODUCT_INTEREST_RATE("R114", "통장 상품 금리가 유효하지 않습니다."),
    INVALID_PASSBOOK_PRODUCT_TERM("R115", "통장 상품 기간이 유효하지 않습니다."),
    INVALID_PASSBOOK_PRODUCT_AMOUNT("R116", "통장 상품 월 납입금이 유효하지 않습니다."),
    INVALID_PASSBOOK_PRODUCT_EXPIRED_AT("R117", "통장 상품 만기일이 유효하지 않습니다."),

    /**
     * T: Transaction
     */
    NONEXISTENT_TRANSACTION("T001", "존재하지 않는 거래내역입니다."),
    // 유효성 확인
    NOT_NULL_TRANSFER_AMOUNT("T101","이체 금액은 필수입니다."),
    NOT_NULL_TRANSFER_MEMO("T102","이체 내용은 필수입니다."),
    NOT_NULL_TRANSFER_COMMISSION("T103", "이체 수수료는 필수입니다."),
    INVALID_TRANSFER_AMOUNT("T104", "이체 금액은 양수여야 합니다."),
    INVALID_TRANSFER_COMMISSION("T105", "이체 수수료는 양수여야 합니다."),
    ;

    private final String code;
    private final String message;


    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
