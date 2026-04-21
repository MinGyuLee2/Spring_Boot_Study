package com.example.demo.global.apiPayload.code;

import org.springframework.http.HttpStatus;

/**
 * 모든 에러 코드 enum이 따라야 하는 공통 규칙입니다.
 *
 * <p>전역 예외 처리기는 이 인터페이스를 통해 HTTP 상태, 에러 코드,
 * 에러 메시지를 동일한 방식으로 꺼내서 응답을 만듭니다.</p>
 */
public interface BaseErrorCode {

    // 클라이언트에게 내려줄 HTTP 상태 코드입니다. 예: 400, 404, 500
    HttpStatus getHttpStatus();

    // 애플리케이션에서 구분하기 위한 에러 코드입니다. 예: MEMBER404, COMMON400
    String getCode();

    // 클라이언트에게 보여줄 에러 메시지입니다.
    String getMessage();
}
