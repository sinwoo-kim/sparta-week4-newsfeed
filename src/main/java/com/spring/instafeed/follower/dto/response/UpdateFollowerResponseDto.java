package com.spring.instafeed.follower.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.instafeed.common.Status;
import com.spring.instafeed.follower.entity.Follower;

import java.time.LocalDateTime;

public record UpdateFollowerResponseDto(
        Long id,
        Status status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {
    public static UpdateFollowerResponseDto toDto(
            Follower follower
    ) {
        return new UpdateFollowerResponseDto(
                follower.getId(),
                follower.getStatus(),
                follower.getCreatedAt(),
                follower.getUpdatedAt()
        );
    }
}