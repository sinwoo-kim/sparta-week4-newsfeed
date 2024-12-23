package com.spring.instafeed.profile.entity;

import com.spring.instafeed.base.BaseEntity;
import com.spring.instafeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @ManyToOne
    private User user;

    private final String nickname;
    private final String content;
    private final String imagePath;

    // 기본 생성자 추가 (JPA에서 필요)
    protected Profile() {
        // 기본 생성자 필요, 필드값은 null로 설정
        this.id = null;
        this.nickname = null;
        this.content = null;
        this.imagePath = null;
    }

    // 불변 객체를 위한 생성자
    public Profile(Long id, String nickname, String content, String imagePath, LocalDateTime createdAt,
                   LocalDateTime updatedAt, Boolean isDeleted, LocalDateTime deletedAt) {
        super(createdAt, updatedAt, isDeleted, deletedAt);  // BaseEntity 호출
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.imagePath = imagePath;
    }

}