package com.spring.instafeed.profile.entity;

import com.spring.instafeed.base.BaseEntity;
import com.spring.instafeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String nickname;
    private String content;
    private String imagePath;

    /**
     * 기본 생성자
     *
     * JPA에서 엔티티 객체를 매핑할 때 사용되는 기본 생성자입니다.
     * JPA는 객체를 DB에서 조회하거나 새로운 객체를 생성할 때 기본 생성자를 호출합니다.
     * 따라서 이 생성자는 JPA에서 객체를 관리하기 위해 필요합니다.
     */
    protected Profile() {
    }

    /**
     * 불변 객체를 위한 생성자
     *
     * 이 생성자는 `Profile` 객체를 불변 객체로 생성하기 위해 사용됩니다.
     * 주로 새로운 `Profile` 객체를 생성할 때 사용되며, 생성 시점에 모든 필드를 초기화합니다.
     *
     * @param user     프로필에 연관된 사용자 정보 (User 객체)
     * @param nickname 사용자의 닉네임
     * @param content  프로필에 대한 설명 또는 내용
     * @param imagePath 프로필 이미지 경로
     */
    public Profile(User user, String nickname, String content, String imagePath) {
        this.user = user;          // 사용자 정보
        this.nickname = nickname;  // 사용자 닉네임
        this.content = content;    // 프로필 내용
        this.imagePath = imagePath; // 프로필 이미지 경로
    }
}