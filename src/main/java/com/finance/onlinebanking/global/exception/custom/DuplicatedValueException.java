package com.finance.onlinebanking.global.exception.custom;


import com.finance.onlinebanking.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicatedValueException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicatedValueException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}