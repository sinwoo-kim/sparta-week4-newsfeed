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
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "profile")
public class Profile extends BaseEntity {

    @Comment("프로필 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    // 한 명의 사용자가 여러 프로필을 가질 수 있도록 변경
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Comment("사용자 닉네임")
    @Column(
            name = "nickname",
            nullable = false,
            unique = true,
            length = 16
    )
    private String nickname;

    @Comment("프로필 내용")
    @Column(
            name = "content",
            nullable = false,
            length = 255
    )
    private String content;

    /*
    todo
     경로 길이를 정하는 게 좋을까? 정한다면,
     만약 경로 길이가 너무 길 땐 현업에서 어떻게 할까?
     */
    @Comment("프로필 이미지 경로")
    @Column(
            name = "image_path",
            nullable = false,
            length = 255
    )
    private String imagePath;

    // 기본 생성자
    protected Profile() {
    }

    /**
     * 생성자
     *
     * @param user      : 프로필에 연관된 사용자
     * @param nickname  : 사용자 프로필의 닉네임
     * @param content   : 사용자 프로필의 소개 내용
     * @param imagePath : 사용자 프로필 이미지의 경로
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