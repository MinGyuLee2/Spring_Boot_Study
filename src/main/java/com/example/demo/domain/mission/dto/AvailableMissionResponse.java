package com.example.demo.domain.mission.dto;

/**
 * 회원이 아직 도전하지 않은 미션 목록의 개별 항목 응답 DTO입니다.
 */
public record AvailableMissionResponse(
        Long missionId,
        String title,
        String description,
        Integer rewardPoint,
        Long storeId,
        String storeName,
        String category,
        Long regionId,
        String regionName
) {
}
