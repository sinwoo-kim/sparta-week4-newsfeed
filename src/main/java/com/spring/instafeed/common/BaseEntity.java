package com.spring.instafeed.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Boolean isDeleted = false;
    private LocalDateTime deletedAt;

    // 불변성 유지하고 생성자 수정
    public BaseEntity(LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isDeleted, LocalDateTime deletedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public void deleteEntity() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now(); // 삭제일자 기록
    }
}