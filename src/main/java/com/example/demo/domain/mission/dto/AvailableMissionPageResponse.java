package com.example.demo.domain.mission.dto;

import java.util.List;

public record AvailableMissionPageResponse(
        List<AvailableMissionResponse> missions,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
