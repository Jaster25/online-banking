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
    NOT_NULL_USER_ID("U101", "아이디는 필수입니다."),
    NOT_NULL_USER_PASSWORD("U102", "비밀번호는 필수입니다."),
    // 회원 가입
    DUPLICATED_USER_ID("U201", "이미 존재하는 사용자 아이디입니다."),
    // 로그인
    LOGIN_FAILURE("U301", "아이디 또는 비밀번호가 잘못 입력되었습니다."),

    /**
     * P: Passbook
     */
    NONEXISTENT_POST("P001", "존재하지 않는 통장입니다."),
    // 유효성 확인
    NOT_NULL_POST_TITLE("P101", " 제목은 필수입니다."),
    NOT_NULL_POST_CONTENT("P102", "게시물 내용은 필수입니다."),

    /**
     * R: Product
     */
    NONEXISTENT_PRODUCT("R001", "존재하지 않는 상품입니다."),
    // 유효성 확인
    NOT_NULL_COMMENT_PRODUCT("R101", "통장 내용은 필수입니다."),

    /**
     * T: Transaction
     */
    NONEXISTENT_TRANSACTION("T001", "존재하지 않는 거래내역입니다."),

    ;

    private final String code;
    private final String message;


    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
