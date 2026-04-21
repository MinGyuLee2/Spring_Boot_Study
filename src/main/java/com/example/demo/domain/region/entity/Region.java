package com.example.demo.domain.region.entity;

import com.example.demo.domain.store.entity.Store;
import com.example.demo.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "region")
public class Region extends BaseTimeEntity {

    // 지역 테이블의 기본 키입니다.
    @Id
    @Column(name = "region_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 지역 이름입니다. 예: 서울, 부산처럼 중복 없이 관리합니다.
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    // 이 지역에 속한 가게 목록입니다.
    @OneToMany(mappedBy = "region")
    private List<Store> stores = new ArrayList<>();
}
