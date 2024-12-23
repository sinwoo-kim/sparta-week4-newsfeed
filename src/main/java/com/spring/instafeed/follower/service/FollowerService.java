package com.spring.instafeed.follower.service;

import com.spring.instafeed.follower.dto.response.FollowerResponseDto;

public interface FollowerService {

    FollowerResponseDto sendFollowRequest(Long senderId, Long receiverId);
}
