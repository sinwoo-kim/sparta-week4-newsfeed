package com.spring.instafeed.newsfeed.entity;

import com.spring.instafeed.common.BaseEntity;
import com.spring.instafeed.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "newsfeed")
@Getter
public class Newsfeed extends BaseEntity {

    @Comment("게시물 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("게시물 내용")
    @Column(
            name = "content",
            nullable = false,
            length = 255
    )
    private String content;

    @Comment("게시물 이미지 경로")
    @Column(
            name = "image_path",
            nullable = false,
            length = 255
    )
    private String imagePath;

    @Comment("게시물을 작성한 사용자의 닉네임")
    @Column(
            name = "nickname",
            nullable = false,
            length = 16
    )
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    protected Newsfeed() {
    }

    public Newsfeed(
            Profile profile,
            String content,
            String imagePath
    ) {
        this.profile = profile;
        this.content = content;
        this.imagePath = imagePath;
        this.nickname = profile.getNickname();
    }

    public static Newsfeed create(
            Profile profile,
            String content,
            String imagePath
    ) {
        return new Newsfeed(
                profile,
                content,
                imagePath
        );
    }

    /**
     * 기능
     * 게시물 내용 수정
     *
     * @param content 수정하려는 게시물의 내용
     */
    public void update(String content) {
        this.content = content;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}