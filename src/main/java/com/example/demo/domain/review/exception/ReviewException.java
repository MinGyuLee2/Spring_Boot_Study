package com.example.demo.domain.review.exception;

import com.example.demo.global.exception.DomainException;

public class ReviewException extends DomainException {

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }
}
