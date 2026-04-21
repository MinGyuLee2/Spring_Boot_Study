package com.example.demo.domain.mission.dto;

import java.util.List;

/**
 * 회원 미션 목록과 페이지 정보를 함께 담는 응답 DTO입니다.
 */
public record MemberMissionPageResponse(
        List<MemberMissionResponse> memberMissions,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
