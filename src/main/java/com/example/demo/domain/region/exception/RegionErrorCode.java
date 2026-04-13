package com.example.demo.domain.region.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RegionErrorCode implements BaseErrorCode {
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION404", "Region not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
