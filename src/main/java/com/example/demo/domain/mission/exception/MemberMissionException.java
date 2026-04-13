package com.example.demo.domain.mission.exception;

import com.example.demo.global.exception.DomainException;

public class MemberMissionException extends DomainException {

    public MemberMissionException(MemberMissionErrorCode errorCode) {
        super(errorCode);
    }
}
