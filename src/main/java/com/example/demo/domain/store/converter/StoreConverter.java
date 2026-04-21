package com.example.demo.domain.store.converter;

import com.example.demo.domain.store.dto.StoreResponse;
import com.example.demo.domain.store.entity.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreConverter {

    /**
     * 가게 엔티티를 응답 DTO로 변환합니다.
     */
    public StoreResponse toResponse(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getRegion().getId(),
                store.getName(),
                store.getAddress(),
                store.getCategory(),
                store.getStatus()
        );
    }
}
