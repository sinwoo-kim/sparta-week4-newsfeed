package com.spring.instafeed.follower.dto.request;

public record CreateFollowerRequestDto(Long senderId, Long receiverId) {
}