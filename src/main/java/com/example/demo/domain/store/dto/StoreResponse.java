package com.example.demo.domain.store.dto;

import com.example.demo.domain.store.enums.StoreStatus;

public record StoreResponse(
        Long storeId,
        Long regionId,
        String name,
        String address,
        String category,
        StoreStatus status
) {
}
