package com.spring.instafeed.follower.entity;

import com.spring.instafeed.base.BaseFollowerEntity;
import com.spring.instafeed.base.Status;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Follower extends BaseFollowerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_profile_id")
    private Profile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_profile_id")
    private Profile receiver;

    @Column(columnDefinition = "VARCHAR(32)")
    @Enumerated(EnumType.STRING)
    private Status status;

    public Follower() {}

    public Follower(Profile sender, Profile receiver, Status status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }
}
