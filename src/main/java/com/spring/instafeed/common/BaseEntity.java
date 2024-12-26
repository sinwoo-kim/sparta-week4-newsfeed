package com.spring.instafeed.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Comment("생성일")
    @CreatedDate
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Comment("수정일")
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    // isDeleted 값을 설정할 수 있는 메서드 추가
    @Comment("삭제 여부")
    @Column(name = "is_deleted", columnDefinition = "TINYINT(0)")

    private Boolean isDeleted = false;


    // 삭제일을 설정할 수 있는 메서드 추가
    @Comment("삭제일")
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public void markAsDeleted() {
        this.isDeleted = true; // 삭제 플래그를 true로 설정
        this.deletedAt = LocalDateTime.now(); // 현재 시간으로 삭제일 설정
    }
}