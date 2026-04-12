package com.example.demo.domain.store.converter;

import com.example.demo.domain.store.dto.StoreResponse;
import com.example.demo.domain.store.entity.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreConverter {

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
