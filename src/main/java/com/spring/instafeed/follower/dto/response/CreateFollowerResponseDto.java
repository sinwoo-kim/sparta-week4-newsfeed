package com.spring.instafeed.follower.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.instafeed.common.Status;
import com.spring.instafeed.follower.entity.Follower;

import java.time.LocalDateTime;

public record CreateFollowerResponseDto(
        Long id,
        Long senderProfileId,
        Long receiverProfileId,
        Enum<Status> status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) {
    public static CreateFollowerResponseDto toDto(
            Follower follower
    ) {
        return new CreateFollowerResponseDto(
                follower.getId(),
                follower.getSenderProfile().getId(),
                follower.getReceiverProfile().getId(),
                follower.getStatus(),
                follower.getCreatedAt()
        );
    }
}