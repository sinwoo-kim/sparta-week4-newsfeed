package com.spring.instafeed.follower.dto.response;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.entity.Follower;
import lombok.Getter;

@Getter
public class UpdateFollowerResponseDto {
    private final Status status;

    public UpdateFollowerResponseDto(Status status) {
        this.status = status;
    }

    public static UpdateFollowerResponseDto toDto(Follower follower) {
        return new UpdateFollowerResponseDto(follower.getStatus());
    }
}