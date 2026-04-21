package com.example.demo.domain.mission.entity;

import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.mission.enums.MissionStatus;
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
@Table(name = "mission")
public class Mission extends BaseTimeEntity {

    // 미션 테이블의 기본 키입니다.
    @Id
    @Column(name = "mission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 미션이 진행되는 가게입니다. 여러 미션이 하나의 가게에 속할 수 있습니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 미션 제목입니다.
    @Column(nullable = false, length = 50)
    private String title;

    // 미션 수행 조건이나 설명입니다.
    @Column(nullable = false, length = 255)
    private String description;

    // 미션 완료 시 지급되는 포인트입니다.
    @Column(name = "reward_point", nullable = false)
    private Integer rewardPoint;

    // 미션이 현재 노출 가능한 상태인지 표현합니다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MissionStatus status;

    // 이 미션에 도전한 회원 미션 목록입니다.
    @OneToMany(mappedBy = "mission")
    private List<MemberMission> memberMissions = new ArrayList<>();
}
