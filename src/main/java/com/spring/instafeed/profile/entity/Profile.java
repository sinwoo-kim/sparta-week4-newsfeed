package com.spring.instafeed.profile.entity;

import com.spring.instafeed.base.BaseEntity;
import com.spring.instafeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "profile")
@AllArgsConstructor
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 한 명의 사용자에 대해 여러 프로필을 가질 수 있도록 변경
    @JoinColumn(name = "user_id")
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

    // 생성자 ㅋㅋㅋㅋㅋ 패턴 통일하자..!!!!!!!!!
    /**
     * Profile 클래스의 생성자입니다.
     *
     * @param user      이 프로필에 연결된 사용자 객체입니다.
     *                  사용자 정보에 따라 프로필의 소유자가 결정됩니다.
     * @param nickname  프로필에서 사용될 사용자 닉네임입니다.
     *                  다른 사용자에게 표시되는 이름으로, 고유해야 할 수 있습니다.
     * @param content   프로필에 대한 추가 정보 또는 설명입니다.
     *                  사용자가 자신의 프로필에 대해 작성한 내용을 나타냅니다.
     * @param imagePath 프로필 이미지의 경로입니다.
     *                  이 경로는 사용자의 프로필 사진을 저장하는 위치를 나타냅니다.
     */
    public Profile(User user, String nickname, String content, String imagePath) {
        this.user = user;
        this.nickname = nickname;
        this.content = content;
        this.imagePath = imagePath;
    }
}