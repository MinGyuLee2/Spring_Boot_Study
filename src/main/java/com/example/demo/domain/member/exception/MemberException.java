package com.example.demo.domain.member.exception;

import com.example.demo.global.exception.DomainException;

public class MemberException extends DomainException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
