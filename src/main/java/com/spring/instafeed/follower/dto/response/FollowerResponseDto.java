package com.spring.instafeed.follower.dto.response;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.entity.Follower;
import lombok.Getter;

@Getter
public class FollowerResponseDto {
    private final Long senderId;
    private final Long receiverId;
    private final Enum<Status> status;

    public FollowerResponseDto(Long senderId, Long receiverId, Enum<Status> status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }

    public static FollowerResponseDto toDto(Follower follower) {
        return new FollowerResponseDto(follower.getSender().getId(), follower.getReceiver().getId(), follower.getStatus());
    }
}
