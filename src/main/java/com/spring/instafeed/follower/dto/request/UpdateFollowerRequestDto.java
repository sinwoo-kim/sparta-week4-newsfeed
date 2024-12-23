package com.spring.instafeed.follower.dto.request;

import com.spring.instafeed.base.Status;
import lombok.Getter;

@Getter
public class UpdateFollowerRequestDto {
    private final Long requestSenderId;
    private final Status status;

    public UpdateFollowerRequestDto(Long requestSenderId, Status status) {
        this.requestSenderId = requestSenderId;
        this.status = status;
    }
}