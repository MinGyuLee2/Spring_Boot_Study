package com.example.demo.domain.region.dto;

/**
 * 지역 단건 조회 응답 DTO입니다.
 */
public record RegionResponse(
        Long regionId,
        String name
) {
}
