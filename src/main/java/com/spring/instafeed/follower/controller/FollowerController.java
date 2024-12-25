package com.spring.instafeed.follower.controller;


import com.spring.instafeed.follower.dto.request.CreateFollowerRequestDto;
import com.spring.instafeed.follower.dto.response.CreateFollowerResponseDto;
import com.spring.instafeed.follower.dto.response.ReadFollowerResponseDto;
import com.spring.instafeed.follower.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/followers")
public class FollowerController {

    private final FollowerService followerService;

    @PostMapping
    public ResponseEntity<CreateFollowerResponseDto> createFollower(
            @RequestBody CreateFollowerRequestDto requestDto
    ) {
        CreateFollowerResponseDto responseDto = followerService.createFollower(
                requestDto.senderProfileId(),
                requestDto.receiverProfileId()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReadFollowerResponseDto>> readAllFollowers() {
        List<ReadFollowerResponseDto> allFollowers = new ArrayList<>();

        allFollowers = followerService.readAllFollowers();

        return new ResponseEntity<>(allFollowers, HttpStatus.OK);
    }
}
