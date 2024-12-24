package com.spring.instafeed.profile.entity;

import com.spring.instafeed.base.BaseEntity;
import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "profile")
@AllArgsConstructor
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 한 명의 사용자에 대해 여러 프로필을 가질 수 있도록 변경
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
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
     * 프로필 생성자
     *
     * @param user      프로필에 연관된 사용자
     * @param nickname  사용자 프로필의 닉네임
     * @param content   사용자 프로필의 소개 내용
     * @param imagePath 사용자 프로필 이미지의 경로
     */
    public Profile(User user, String nickname, String content, String imagePath) {
        this.user = user;  // 주어진 사용자로 필드 초기화
        this.nickname = nickname;  // 주어진 닉네임으로 필드 초기화
        this.content = content;  // 주어진 내용으로 필드 초기화
        this.imagePath = imagePath;  // 주어진 이미지 경로로 필드 초기화
    }

    /**
     * 프로필 업데이트 메서드
     *
     * 프로필의 닉네임, 내용, 이미지 경로를 업데이트합니다.
     *
     * @param nickname  새 닉네임
     * @param content   새 소개 내용
     * @param imagePath 새 이미지 경로
     */
    public void updateProfile(String nickname, String content, String imagePath) {
        this.nickname = nickname;  // 닉네임을 새로운 값으로 업데이트
        this.content = content;  // 내용을 새로운 값으로 업데이트
        this.imagePath = imagePath;  // 이미지 경로를 새로운 값으로 업데이트
    }
}