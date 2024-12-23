package com.spring.instafeed.follower.dto.response;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.entity.Follower;

public record UpdateFollowerResponseDto(Status status) {

    public static UpdateFollowerResponseDto toDto(Follower follower) {
        return new UpdateFollowerResponseDto(follower.getStatus());
    }
}