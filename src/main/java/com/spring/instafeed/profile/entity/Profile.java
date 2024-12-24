package com.spring.instafeed.profile.entity;

import com.spring.instafeed.base.BaseEntity;
import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
@Table(name = "profile")
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
     * <p>
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
    public Profile(
            User user,
            String nickname,
            String content,
            String imagePath
    ) {
        this.user = user;
        this.nickname = nickname;
        this.content = content;
        this.imagePath = imagePath;
    }

    /**
     * 기능
     * DTO로부터 프로필 객체를 생성하는 정적 메서드
     *
     * @param user 프로필에 연관된 사용자
     * @param dto  프로필 생성 요청 데이터 전송 객체
     * @return 생성된 프로필 객체
     */
    public static Profile create(
            User user,
            CreateProfileRequestDto dto
    ) {
        return new Profile(
                user,
                dto.nickname(),
                dto.content(),
                dto.imagePath()
        );
    }

    /**
     * 기능
     * 프로필 정보를 DTO로부터 업데이트하는 메서드
     *
     * @param dto 프로필 수정 요청 데이터 전송 객체
     */
    public void update(UpdateProfileRequestDto dto) {
        this.nickname = dto.nickname();
        this.content = dto.content();
        this.imagePath = dto.imagePath();
    }
}