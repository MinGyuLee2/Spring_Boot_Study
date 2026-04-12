package com.example.demo.domain.store.service;

import com.example.demo.domain.store.converter.StoreConverter;
import com.example.demo.domain.store.dto.StoreResponse;
import com.example.demo.domain.store.exception.StoreErrorCode;
import com.example.demo.domain.store.exception.StoreException;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreConverter storeConverter;

    public StoreResponse getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .map(storeConverter::toResponse)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
    }
}
