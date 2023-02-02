package com.finance.onlinebanking.global.exception;

import com.finance.onlinebanking.global.exception.custom.DuplicatedValueException;
import com.finance.onlinebanking.global.exception.custom.InvalidValueException;
import com.finance.onlinebanking.global.exception.custom.NonExistentException;
import com.finance.onlinebanking.global.exception.custom.UnAuthenticatedException;
import com.finance.onlinebanking.global.exception.custom.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * HTTP status: 400
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.valueOf(exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = InvalidValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidValueException(InvalidValueException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_REQUEST, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * HttpStatus: 401
     */
    @ExceptionHandler(value = UnAuthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthenticatedException(UnAuthenticatedException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode(), request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * HttpStatus: 403
     */
    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode(), request);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * HttpStatus: 404
     */
    @ExceptionHandler(value = NonExistentException.class)
    public ResponseEntity<ErrorResponse> handleNonExistentException(NonExistentException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * HTTP status: 409
     * */
    @ExceptionHandler(value = DuplicatedValueException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedValueException(DuplicatedValueException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode(), request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
