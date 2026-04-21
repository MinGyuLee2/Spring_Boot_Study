package com.example.demo.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {

    // 엔티티가 처음 저장된 시간을 기록합니다.
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 엔티티가 마지막으로 수정된 시간을 기록합니다.
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 엔티티가 DB에 처음 저장되기 직전에 실행됩니다.
     *
     * <p>{@code @PrePersist}는 JPA 생명주기 콜백 중 하나입니다.
     * 생성 시점에는 createdAt과 updatedAt을 같은 시간으로 맞춥니다.</p>
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * 이미 저장된 엔티티가 수정되어 DB에 반영되기 직전에 실행됩니다.
     *
     * <p>수정 시점에는 생성 시간은 유지하고 updatedAt만 현재 시간으로 갱신합니다.</p>
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
