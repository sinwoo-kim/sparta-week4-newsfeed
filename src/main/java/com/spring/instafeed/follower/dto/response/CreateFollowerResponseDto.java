package com.spring.instafeed.follower.dto.response;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.entity.Follower;

public record CreateFollowerResponseDto(
        Long senderProfileId,
        Long receiverProfileId,
        Enum<Status> status
) {
    public static CreateFollowerResponseDto toDto(Follower follower) {
        return new CreateFollowerResponseDto(
                follower.getSenderProfile().getId(),
                follower.getReceiverProfile().getId(),
                follower.getStatus()
        );
    }
}