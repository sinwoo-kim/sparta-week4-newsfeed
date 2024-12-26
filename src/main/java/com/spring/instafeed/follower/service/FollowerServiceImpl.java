package com.spring.instafeed.follower.service;

import com.spring.instafeed.common.Status;
import com.spring.instafeed.exception.data.DataNotFoundException;
import com.spring.instafeed.exception.invalid.InvalidFollowRequestException;
import com.spring.instafeed.follower.dto.response.*;
import com.spring.instafeed.follower.entity.Follower;
import com.spring.instafeed.follower.repository.FollowerRepository;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowerServiceImpl implements FollowerService {
    // 속성
    private final FollowerRepository followerRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    @Override
    public CreateFollowerResponseDto createFollower(
            Long senderProfileId,
            Long receiverProfileId
    ) {
        Profile senderProfile = profileRepository
                .findById(senderProfileId)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        Profile receiverProfile = profileRepository
                .findById(receiverProfileId)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        // 사용자가 자기 자신을 팔로잉 요청하는지 검증 시작
        Long senderUserId = senderProfile.getUser().getId();
        Long receiverUserId = receiverProfile.getUser().getId();

        if (senderUserId.equals(receiverUserId)) {
            throw new InvalidFollowRequestException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot follow your own profile"
            );
        }
        // 사용자가 자기 자신을 팔로잉 요청하는지 검증 종료

        // 동일한 팔로잉 요청이 있는지 검증 시작
        followerRepository
                .findBySenderProfileIdAndReceiverProfileId(
                        senderProfileId,
                        receiverProfileId
                )
                .ifPresent(follower -> {
                            throw new InvalidFollowRequestException(
                                    HttpStatus.BAD_REQUEST,
                                    "The requested data already exists"
                            );
                        }
                );
        // 동일한 팔로잉 요청이 있는지 검증 종료

        // 반대로 팔로잉 요청이 있는지 검증 시작
        followerRepository
                .findBySenderProfileIdAndReceiverProfileId(
                        receiverProfileId,
                        senderProfileId
                )
                .ifPresent(reverseFollower -> {
                            throw new InvalidFollowRequestException(
                                    HttpStatus.BAD_REQUEST,
                                    "The reverse requested data already exists"
                            );
                        }
                );
        // 반대로 팔로잉 요청이 있는지 검증 종료

        Follower followerToSave = Follower.create(
                senderProfile,
                receiverProfile,
                Status.PENDING
        );
        Follower savedFollower = followerRepository
                .save(followerToSave);

        return CreateFollowerResponseDto.toDto(savedFollower);
    }

    @Override
    public List<ReadFollowerResponseDto> readAllFollowers() {

        List<Follower> followers = new ArrayList<>();

        followers = followerRepository.findAllByStatusIsNotNull();

        List<ReadFollowerResponseDto> allFollowers = new ArrayList<>();

        allFollowers = followers.stream()
                .map(ReadFollowerResponseDto::toDto)
                .toList();

        return allFollowers;
    }

    @Transactional
    @Override
    public UpdateFollowerResponseDto updateFollower(
            Long id,
            Status status
    ) {
        Follower foundFollower = followerRepository.findById(id)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        foundFollower.update(status);

        return UpdateFollowerResponseDto.toDto(foundFollower);
    }
}