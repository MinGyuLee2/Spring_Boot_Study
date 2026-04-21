package com.example.demo.domain.store.entity;

import com.example.demo.domain.mission.entity.Mission;
import com.example.demo.domain.region.entity.Region;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.store.enums.StoreStatus;
import com.example.demo.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "store")
public class Store extends BaseTimeEntity {

    // 가게 테이블의 기본 키입니다.
    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 가게가 속한 지역입니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    // 가게 이름입니다.
    @Column(nullable = false, length = 50)
    private String name;

    // 가게 주소입니다.
    @Column(nullable = false, length = 50)
    private String address;

    // 음식점 카테고리입니다. 예: 한식, 중식, 카페 등입니다.
    @Column(nullable = false, length = 50)
    private String category;

    // 가게 운영 상태입니다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StoreStatus status;

    // 이 가게에서 제공하는 미션 목록입니다.
    @OneToMany(mappedBy = "store")
    private List<Mission> missions = new ArrayList<>();

    // 이 가게에 작성된 리뷰 목록입니다.
    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();
}
