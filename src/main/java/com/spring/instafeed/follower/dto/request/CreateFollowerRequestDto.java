package com.spring.instafeed.follower.dto.request;

public record CreateFollowerRequestDto(
        Long senderProfileId,
        Long receiverProfileId
) {
}