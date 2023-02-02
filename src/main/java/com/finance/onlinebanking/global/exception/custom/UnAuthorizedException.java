package com.finance.onlinebanking.global.exception.custom;


import com.finance.onlinebanking.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException {

    private final ErrorCode errorCode;

    public UnAuthorizedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
