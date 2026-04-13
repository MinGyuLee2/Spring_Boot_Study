package com.example.demo.domain.mission.dto;

import java.util.List;

public record MemberMissionPageResponse(
        List<MemberMissionResponse> memberMissions,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
