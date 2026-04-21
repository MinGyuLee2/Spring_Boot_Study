package com.example.demo.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class SoftDeleteBaseEntity extends BaseTimeEntity {

    // 실제 row를 삭제하지 않고, 삭제된 시간을 기록해서 삭제 상태를 표현합니다.
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * 소프트 삭제 처리를 합니다.
     *
     * <p>DB에서 데이터를 물리적으로 지우지 않고 deletedAt에 현재 시간을 넣습니다.
     * 이렇게 하면 나중에 복구하거나 삭제 이력을 확인할 수 있습니다.</p>
     */
    public void markDeleted() {
        this.deletedAt = LocalDateTime.now();
    }
}
