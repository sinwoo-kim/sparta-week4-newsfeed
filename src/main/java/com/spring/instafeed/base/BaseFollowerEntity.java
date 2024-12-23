package com.spring.instafeed.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseFollowerEntity {

    @Comment("수락일")
    @Column(name = "accepted_at", updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime acceptedAt;

    @Comment("삭제일")
    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public BaseFollowerEntity(LocalDateTime acceptedAt, LocalDateTime deletedAt) {
        this.acceptedAt = acceptedAt;
        this.deletedAt = deletedAt;
    }
}
