package com.finance.onlinebanking.global.exception.custom;


import com.finance.onlinebanking.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NonExistentException extends RuntimeException {

    private final ErrorCode errorCode;

    public NonExistentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
