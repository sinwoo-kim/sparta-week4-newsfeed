package com.spring.instafeed.follower.dto.response;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.entity.Follower;

public record ReadFollowerResponseDto(
        Long senderProfileId,
        Long receiverProfileId,
        Enum<Status> status
) {
    public static ReadFollowerResponseDto toDto(Follower follower) {
        return new ReadFollowerResponseDto(
                follower.getSenderProfile().getId(),
                follower.getReceiverProfile().getId(),
                follower.getStatus()
        );
    }
}