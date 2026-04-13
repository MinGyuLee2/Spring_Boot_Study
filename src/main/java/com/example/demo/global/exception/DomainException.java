package com.example.demo.global.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public DomainException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
