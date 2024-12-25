package com.spring.instafeed.follower.service;

import com.spring.instafeed.follower.dto.response.CreateFollowerResponseDto;
import com.spring.instafeed.follower.dto.response.ReadFollowerResponseDto;

import java.util.List;

public interface FollowerService {

    CreateFollowerResponseDto createFollower(
            Long senderProfileId,
            Long receiverProfileId
    );

    List<ReadFollowerResponseDto> readAllFollowers();
}
