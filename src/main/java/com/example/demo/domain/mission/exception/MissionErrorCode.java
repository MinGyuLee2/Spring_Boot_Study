package com.example.demo.domain.mission.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404", "Mission not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
