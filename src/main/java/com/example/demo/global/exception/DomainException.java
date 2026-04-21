package com.example.demo.global.exception;

import com.example.demo.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

/**
 * 서비스 로직에서 의도적으로 발생시키는 도메인 예외의 부모 클래스입니다.
 *
 * <p>MemberException, StoreException 같은 예외들이 이 클래스를 상속하면
 * GlobalExceptionHandler에서 한 번에 공통 방식으로 처리할 수 있습니다.</p>
 */
@Getter
public class DomainException extends RuntimeException {

    // 응답에 사용할 HTTP 상태, 에러 코드, 메시지를 담고 있습니다.
    private final BaseErrorCode errorCode;

    public DomainException(BaseErrorCode errorCode) {
        // RuntimeException의 message에도 에러 메시지를 넣어 로그나 디버깅에서 확인할 수 있게 합니다.
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
