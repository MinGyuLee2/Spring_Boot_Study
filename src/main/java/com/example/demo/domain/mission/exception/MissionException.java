package com.example.demo.domain.mission.exception;

import com.example.demo.global.exception.DomainException;

public class MissionException extends DomainException {

    public MissionException(MissionErrorCode errorCode) {
        super(errorCode);
    }
}
