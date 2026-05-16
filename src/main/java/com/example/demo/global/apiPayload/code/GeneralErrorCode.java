package com.example.demo.global.apiPayload.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 특정 도메인에 속하지 않는 공통 에러 코드입니다.
 *
 * <p>잘못된 요청, 인증 실패, 서버 내부 오류처럼 여러 API에서 공통으로
 * 사용할 수 있는 에러를 여기에 정의합니다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "접근 권한이 없습니다.");

    // HTTP 상태, 서비스 에러 코드, 응답 메시지를 한 세트로 관리합니다.
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
