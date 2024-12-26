package com.spring.instafeed.follower.service;

import com.spring.instafeed.common.Status;
import com.spring.instafeed.follower.dto.response.*;

import java.util.List;

public interface FollowerService {

    CreateFollowerResponseDto createFollower(
            Long senderProfileId,
            Long receiverProfileId
    );

    List<ReadFollowerResponseDto> readAllFollowers();

    UpdateFollowerResponseDto updateFollower(
            Long id,
            Status status
    );
}