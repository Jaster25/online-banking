package com.finance.onlinebanking.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final String method;

    private final String path;

    private final String message;

    private final String code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime timestamp;


    @Builder
    private ErrorResponse(String method, String path, String message, String code) {
        this.method = method;
        this.path = path;
        this.message = message;
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(ErrorCode errorCode, HttpServletRequest request) {
        return ErrorResponse.builder()
                .method(request.getMethod())
                .path(request.getRequestURI())
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();
    }
}
