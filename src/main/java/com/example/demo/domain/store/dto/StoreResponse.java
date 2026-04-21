package com.example.demo.domain.store.dto;

import com.example.demo.domain.store.enums.StoreStatus;

/**
 * 가게 단건 조회 응답 DTO입니다.
 */
public record StoreResponse(
        Long storeId,
        Long regionId,
        String name,
        String address,
        String category,
        StoreStatus status
) {
}
