package com.spring.instafeed.follower.entity;

import com.spring.instafeed.common.BaseFollowerEntity;
import com.spring.instafeed.common.Status;
import com.spring.instafeed.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Follower extends BaseFollowerEntity {

    @Comment("팔로잉 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_profile_id")
    private Profile senderProfile;

    @ManyToOne
    @JoinColumn(name = "receiver_profile_id")
    private Profile receiverProfile;

    @Comment("팔로잉 상태")
    @Column(length = 32)
    @Enumerated(EnumType.STRING)
    private Status status;

    protected Follower() {
    }

    /**
     * 생성자
     *
     * @param senderProfile   : 보내는 사람의 프로필
     * @param receiverProfile : 받는 사람의 프로필
     * @param status   : 팔로잉 상태
     */
    public Follower(
            Profile senderProfile,
            Profile receiverProfile,
            Status status
    ) {
        this.senderProfile = senderProfile;
        this.receiverProfile = receiverProfile;
        this.status = status;
    }

    public static Follower create(
            Profile senderProfile,
            Profile receiverProfile,
            Status status
    ) {
        return new Follower(
                senderProfile,
                receiverProfile,
                status
        );
    }

    public void update (Status status) {
        this.status = status;
    }
}