package com.spring.instafeed.follower.dto.response;

import com.spring.instafeed.common.Status;
import com.spring.instafeed.follower.entity.Follower;

public record UpdateFollowerResponseDto(
        Long id,
        Status status
) {
    public static UpdateFollowerResponseDto toDto(
            Follower follower
    ) {
        return new UpdateFollowerResponseDto(
                follower.getId(),
                follower.getStatus()
        );
    }
}