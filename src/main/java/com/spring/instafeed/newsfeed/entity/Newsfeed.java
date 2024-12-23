package com.spring.instafeed.newsfeed.entity;

import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.base.BaseEntity;
import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "newsfeed")
@Getter
@NoArgsConstructor
public class Newsfeed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsfeedId;
    @Column(nullable = false, unique = true) // null 허용 안함
    private String nickname;
    private String imagePath;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User foundUser;

    // 통일성 주자
    @Builder
    private Newsfeed(String imagePath, String nickname, String content, User foundUser) {
        this.imagePath = imagePath;
        this.nickname = nickname;
        this.content = content;
        this.foundUser = foundUser;

    }

    public static Newsfeed of(NewsfeedCreateRequestDto createRequestDto, User foundUser) {
        return Newsfeed.builder()
                .imagePath(createRequestDto.getImagePath())
                .nickname(createRequestDto.getNickname())
                .content(createRequestDto.getContent())
                .foundUser(foundUser)
                .build();
    }
}
