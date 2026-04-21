package com.example.demo.domain.store.exception;

import com.example.demo.global.exception.DomainException;

/**
 * 가게 도메인에서 발생하는 비즈니스 예외입니다.
 *
 * <p>예외가 발생한 이유는 StoreErrorCode로 전달하고,
 * 실제 HTTP 응답 변환은 GlobalExceptionHandler가 담당합니다.</p>
 */
public class StoreException extends DomainException {

    public StoreException(StoreErrorCode errorCode) {
        super(errorCode);
    }
}
