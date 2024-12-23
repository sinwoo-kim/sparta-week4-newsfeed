package com.spring.instafeed.follower.service;

import com.spring.instafeed.follower.dto.response.FollowerResponseDto;

import java.util.List;

public interface FollowerService {

    FollowerResponseDto sendFollowRequest(Long senderId, Long receiverId);

    List<FollowerResponseDto> findAll();
}
