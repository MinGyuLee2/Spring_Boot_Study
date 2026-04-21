package com.example.demo.global.exception;

import com.example.demo.global.apiPayload.ApiResponse;
import com.example.demo.global.apiPayload.code.BaseErrorCode;
import com.example.demo.global.apiPayload.code.GeneralErrorCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 애플리케이션 전체에서 발생하는 예외를 한곳에서 처리하는 클래스입니다.
 *
 * <p>컨트롤러나 서비스에서 예외가 발생하면 Spring이 이 클래스의
 * {@code @ExceptionHandler} 메서드 중 알맞은 메서드를 찾아 실행합니다.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 도메인에서 의도적으로 발생시킨 비즈니스 예외를 처리합니다.
     *
     * <p>MemberException, StoreException처럼 DomainException을 상속한 예외는
     * 각 예외가 가진 ErrorCode의 HTTP 상태, 코드, 메시지로 응답합니다.</p>
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResponse<Void>> handleDomainException(DomainException exception) {
        // 도메인 예외 안에 들어 있는 에러 코드에서 HTTP 상태와 메시지를 꺼냅니다.
        BaseErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode));
    }

    /**
     * {@code @Valid}가 붙은 요청 DTO의 검증 실패를 처리합니다.
     *
     * <p>예: {@code @NotBlank}, {@code @NotNull}, {@code @Min} 같은 조건을
     * 만족하지 못하면 400 Bad Request 응답을 반환합니다.</p>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST));
    }

    /**
     * 요청 형식이나 파라미터가 잘못된 경우를 처리합니다.
     *
     * <p>예: 필수 파라미터 누락, 타입 변환 실패, JSON 파싱 실패,
     * 잘못된 enum 값, IllegalArgumentException 발생 시 400 Bad Request 응답을 반환합니다.</p>
     */
    @ExceptionHandler({
            ServletRequestBindingException.class,
            MethodArgumentTypeMismatchException.class,
            HandlerMethodValidationException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(Exception exception) {
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST));
    }

    /**
     * 위의 핸들러에서 처리하지 못한 모든 예외를 마지막으로 처리합니다.
     *
     * <p>NullPointerException, DB 연결 문제, 예상하지 못한 런타임 오류처럼
     * 서버 내부 문제로 보는 예외는 500 Internal Server Error 응답을 반환합니다.</p>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        return ResponseEntity.status(GeneralErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.INTERNAL_SERVER_ERROR));
    }
}
