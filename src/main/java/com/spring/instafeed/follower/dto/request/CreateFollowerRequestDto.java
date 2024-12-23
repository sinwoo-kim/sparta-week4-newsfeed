package com.spring.instafeed.follower.dto.request;

import lombok.Getter;

@Getter
public class CreateFollowerRequestDto {
    private final Long senderId;
    private final Long receiverId;

    public CreateFollowerRequestDto(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
