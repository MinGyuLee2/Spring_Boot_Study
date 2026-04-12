package com.example.demo.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();
}
