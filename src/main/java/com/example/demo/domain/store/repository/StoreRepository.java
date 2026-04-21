package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Store 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface StoreRepository extends JpaRepository<Store, Long> {
}
