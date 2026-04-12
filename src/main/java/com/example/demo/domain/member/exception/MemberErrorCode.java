package com.example.demo.domain.member.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "Member not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
