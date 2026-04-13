package com.example.demo.domain.store.exception;

import com.example.demo.global.exception.DomainException;

public class StoreException extends DomainException {

    public StoreException(StoreErrorCode errorCode) {
        super(errorCode);
    }
}
