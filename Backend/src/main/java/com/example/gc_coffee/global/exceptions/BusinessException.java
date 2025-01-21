package com.example.gc_coffee.global.exceptions;

import com.example.gc_coffee.global.exceptions.constant.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private ErrorCode resultCode;
    private String message;

    public BusinessException() {
        super();
        this.resultCode = ErrorCode.UNKNOWN;
    }

    public BusinessException(ErrorCode resultCode) {
        this.resultCode = resultCode;
        this.message = resultCode.getMessage();
    }

    public BusinessException(ErrorCode resultCode, String message) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
        this.message = message;
    }
}
