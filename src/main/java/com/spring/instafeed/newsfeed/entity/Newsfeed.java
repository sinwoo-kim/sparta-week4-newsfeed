package com.spring.instafeed.newsfeed.entity;

import com.spring.instafeed.BaseEntity;
import com.spring.instafeed.User;
import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "newsfeed")
@Getter
public class Newsfeed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsfeedId;
    @Column(nullable = false, unique = true) // null 허용 안함
    private String nickname;
    private String imagePath;
    private String content;
    private Boolean is_deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User foundUser;

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
