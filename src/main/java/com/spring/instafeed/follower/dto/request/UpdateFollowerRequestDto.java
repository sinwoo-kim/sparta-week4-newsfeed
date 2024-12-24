package com.spring.instafeed.follower.dto.request;

import com.spring.instafeed.base.Status;

public record UpdateFollowerRequestDto(Long requestSenderId, Status status) {
}