package com.example.demo.domain.mission.dto;

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
