package com.spring.instafeed.follower.service;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.dto.response.FollowerResponseDto;
import com.spring.instafeed.follower.dto.response.UpdateFollowerResponseDto;

import java.util.List;

public interface FollowerService {

    FollowerResponseDto sendFollowRequest(
            Long senderId,
            Long receiverId
    );

    List<FollowerResponseDto> findAll();

    UpdateFollowerResponseDto updateFollowingStatus(
            Long id,
            Long requestSenderId,
            Status status
    );
}