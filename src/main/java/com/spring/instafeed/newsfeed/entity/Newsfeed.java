package com.spring.instafeed.newsfeed.entity;

import com.spring.instafeed.newsfeed.dto.request.NewsfeedModifyRequestDto;
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

    @Builder
    private Newsfeed(String imagePath, String nickname, String content, User foundUser) {
        this.imagePath = imagePath;
        this.nickname = nickname;
        this.content = content;
        this.foundUser = foundUser;

    }

    /**
     * 게시물(Newsfeed) 엔터티 생성 메서드
     *
     * 이 메서드는 요청 DTO와 사용자 정보를 기반으로 새로운 Newsfeed 객체를 생성합니다.
     * 빌더 패턴을 사용하여 객체를 구성하며, 필수 필드 값들은 DTO 및 사용자 정보를 통해 설정됩니다.
     *
     * @param createRequestDto 게시물 생성 요청 정보를 담은 DTO
     *                         - imagePath: 이미지 경로
     *                         - nickname: 작성자 닉네임
     *                         - content: 게시물 내용
     *
     * @param foundUser 게시물 작성자(User 객체)
     * @return Newsfeed 생성된 엔티티 객체
     */
    public static Newsfeed of(NewsfeedCreateRequestDto createRequestDto, User foundUser) {
        return Newsfeed.builder()
                .imagePath(createRequestDto.getImagePath())
                .nickname(createRequestDto.getNickname())
                .content(createRequestDto.getContent())
                .foundUser(foundUser)
                .build();
    }

    /**
     * 엔티티 업데이트 메서드
     *
     * 이 메서드는 조회 된 기존 newsfeed 객체의 내용을 수정합니다.
     * 요청 DTO에 포함된 변경 사항을 엔티티에 반영하며,
     * 수정된 객체 자신(this)를 반환합니다.
     *
     * @param modifyRequestDto 게시물 수정 요청 정보를 담은 DTO
     *                         - content: 수정할 게시물 내용
     * @return Newsfeed 수정된 엔티티 객체 (this 반환)
     */
    public Newsfeed updateNewsfeed(NewsfeedModifyRequestDto modifyRequestDto) {
        // TODO :: null 체크 해야 되는가?
        this.content = modifyRequestDto.getContent();
        return this;
    }
}
