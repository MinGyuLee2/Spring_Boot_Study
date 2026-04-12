package com.example.demo.domain.mission.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberMissionErrorCode implements BaseErrorCode {
    MEMBER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_MISSION404", "Member mission not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
