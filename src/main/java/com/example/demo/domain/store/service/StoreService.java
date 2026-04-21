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

    // 가게 엔티티를 조회하는 Repository입니다.
    private final StoreRepository storeRepository;

    // Store 엔티티를 응답 DTO로 변환합니다.
    private final StoreConverter storeConverter;

    /**
     * 가게 ID로 단건 조회합니다.
     */
    public StoreResponse getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .map(storeConverter::toResponse)
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
    }
}
