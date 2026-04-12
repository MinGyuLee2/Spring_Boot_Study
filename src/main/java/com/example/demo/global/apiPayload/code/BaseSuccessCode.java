package com.example.demo.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseSuccessCode {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();
}
