package com.spring.instafeed.newsfeed.entity;

import com.spring.instafeed.base.BaseEntity;
import com.spring.instafeed.newsfeed.dto.request.CreateNewsfeedRequestDto;
import com.spring.instafeed.newsfeed.dto.request.UpdateNewsfeedRequestDto;
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
    private Long newsfeedId;

    @Comment("게시물 이미지 경로")
    @Column(
            name = "image_path",
            nullable = false,
            length = 255
    )
    private String imagePath;

    @Comment("게시물 내용")
    @Column(
            name = "content",
            nullable = false,
            length = 255
    )
    private String content;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    Profile profile;

    protected Newsfeed() {
    }

    /**
     * 생성자
     *
     * @param imagePath : 게시물의 이미지 경로
     * @param content   : 게시물의 내용
     * @param profile   : 게시물을 작성한 사용자의 프로필
     */
    private Newsfeed(
            String imagePath,
            String content,
            Profile profile
    ) {
        this.imagePath = imagePath;
        this.content = content;
        this.profile = profile;
    }

    /**
     * 기능
     * DTO와 사용자 정보를 기반으로 새로운 객체 생성
     *
     * @param dto     : 게시물 생성 요청 정보를 담은 DTO
     * @param profile : 게시물 작성자(User 객체)
     * @return 생성된 객체
     */
    public static Newsfeed create(
            CreateNewsfeedRequestDto dto,
            Profile profile
    ) {
        return new Newsfeed(
                dto.imagePath(),
                dto.content(),
                profile
        );
    }

    /**
     * 기능
     * 게시물 내용 수정
     *
     * @param dto 게시물 수정 요청 정보를 담은 DTO
     */
    public void updateNewsfeed(UpdateNewsfeedRequestDto dto) {
        // TODO :: null 체크 해야 여기서 해야 하나?
        this.content = dto.content();
    }
}
