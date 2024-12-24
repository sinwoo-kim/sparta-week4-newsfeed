package com.spring.instafeed.follower.dto.response;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.entity.Follower;

public record FollowerResponseDto(Long senderId, Long receiverId, Enum<Status> status) {

    public static FollowerResponseDto toDto(Follower follower) {
        return new FollowerResponseDto(follower.getSender().getId(), follower.getReceiver().getId(), follower.getStatus());
    }
}
