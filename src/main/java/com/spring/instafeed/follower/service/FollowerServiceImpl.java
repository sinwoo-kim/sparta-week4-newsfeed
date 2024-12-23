package com.spring.instafeed.follower.service;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.dto.response.FollowerResponseDto;
import com.spring.instafeed.follower.entity.Follower;
import com.spring.instafeed.follower.repository.FollowerRepository;
import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @Override
    public FollowerResponseDto sendFollowRequest(Long senderId, Long receiverId) {

        User sendingUser = userRepository.findByIdOrElseThrow(senderId);
        User receivingUser = userRepository.findByIdOrElseThrow(receiverId);

        Follower follower = new Follower(sendingUser, receivingUser, Status.PENDING);

        Follower savedFollower = followerRepository.save(follower);

        return FollowerResponseDto.toDto(savedFollower);
    }
}
