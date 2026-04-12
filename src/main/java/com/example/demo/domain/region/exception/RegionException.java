package com.example.demo.domain.region.exception;

import com.example.demo.global.exception.DomainException;

public class RegionException extends DomainException {

    public RegionException(RegionErrorCode errorCode) {
        super(errorCode);
    }
}
