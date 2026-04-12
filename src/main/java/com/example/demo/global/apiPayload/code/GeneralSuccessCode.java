package com.example.demo.global.apiPayload.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {
    OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    CREATED(HttpStatus.CREATED, "COMMON201", "생성되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
