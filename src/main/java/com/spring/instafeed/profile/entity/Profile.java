package com.spring.instafeed.profile.entity;

import com.spring.instafeed.common.BaseEntity;
import com.spring.instafeed.user.entity.User;
import jakarta.persistence.*;
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

    public static Profile create(
            User user,
            String nickname,
            String content,
            String imagePath
    ) {
        return new Profile(
                user,
                nickname,
                content,
                imagePath
        );
    }

    public void update(
            String nickname,
            String content,
            String imagePath
    ) {
        this.nickname = nickname;
        this.content = content;
        this.imagePath = imagePath;
    }
}