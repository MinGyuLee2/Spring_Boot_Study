package com.example.demo.domain.mission.entity;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.mission.enums.MemberMissionStatus;
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
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_mission")
public class MemberMission extends BaseTimeEntity {

    // 회원 미션 테이블의 기본 키입니다.
    @Id
    @Column(name = "member_mission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 미션에 도전한 회원입니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 회원이 도전한 실제 미션입니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    // 회원의 미션 진행 상태입니다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberMissionStatus status;

    // 미션 도전을 시작한 시간입니다.
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    // 미션을 완료한 시간입니다. 완료 전에는 null입니다.
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * 회원 미션을 완료 상태로 변경하고 완료 시간을 기록합니다.
     */
    public void complete() {
        this.status = MemberMissionStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
}
