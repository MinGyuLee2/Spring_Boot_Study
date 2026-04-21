package com.example.demo.global.apiPayload.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 특정 도메인에 속하지 않는 공통 성공 코드입니다.
 *
 * <p>대부분의 API에서 공통으로 사용할 수 있는 성공 응답을 여기에 정의합니다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {
    OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    CREATED(HttpStatus.CREATED, "COMMON201", "생성되었습니다.");

    // HTTP 상태, 서비스 성공 코드, 응답 메시지를 한 세트로 관리합니다.
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
