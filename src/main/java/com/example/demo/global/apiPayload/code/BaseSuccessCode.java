package com.example.demo.global.apiPayload.code;

import org.springframework.http.HttpStatus;

/**
 * 모든 성공 코드 enum이 따라야 하는 공통 규칙입니다.
 *
 * <p>성공 응답을 만들 때 HTTP 상태, 성공 코드, 메시지를
 * 동일한 방식으로 꺼낼 수 있게 해줍니다.</p>
 */
public interface BaseSuccessCode {

    // 클라이언트에게 내려줄 HTTP 상태 코드입니다. 예: 200, 201
    HttpStatus getHttpStatus();

    // 애플리케이션에서 구분하기 위한 성공 코드입니다. 예: COMMON200
    String getCode();

    // 클라이언트에게 보여줄 성공 메시지입니다.
    String getMessage();
}
