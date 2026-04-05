package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
