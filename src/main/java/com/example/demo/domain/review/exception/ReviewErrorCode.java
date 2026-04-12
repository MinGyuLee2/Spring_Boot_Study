package com.example.demo.domain.review.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404", "Review not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
