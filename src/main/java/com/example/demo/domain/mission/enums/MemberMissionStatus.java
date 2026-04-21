package com.example.demo.domain.mission.enums;

/**
 * 회원이 미션을 진행하는 상태를 표현하는 enum입니다.
 */
public enum MemberMissionStatus {
    // 회원이 현재 도전 중인 미션입니다.
    CHALLENGING,

    // 회원이 완료한 미션입니다.
    COMPLETED,

    // 회원이 실패한 미션입니다.
    FAILED
}
