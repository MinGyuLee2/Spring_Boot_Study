package com.example.demo.domain.region.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 지역 도메인에서 사용할 에러 코드 목록입니다.
 *
 * <p>각 항목은 HTTP 상태, 애플리케이션 에러 코드, 클라이언트 메시지를 함께 가집니다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum RegionErrorCode implements BaseErrorCode {
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION404", "Region not found");

    // GlobalExceptionHandler가 이 값들을 읽어 실패 응답을 만듭니다.
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
