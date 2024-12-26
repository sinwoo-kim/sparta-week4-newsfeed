package com.spring.instafeed.follower.controller;


import com.spring.instafeed.follower.dto.request.CreateFollowerRequestDto;
import com.spring.instafeed.follower.dto.request.UpdateFollowerRequestDto;
import com.spring.instafeed.follower.dto.response.CreateFollowerResponseDto;
import com.spring.instafeed.follower.dto.response.ReadFollowerResponseDto;
import com.spring.instafeed.follower.dto.response.UpdateFollowerResponseDto;
import com.spring.instafeed.follower.service.FollowerServiceImpl;
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

    // 속성
    private final FollowerServiceImpl followerService;

    @PostMapping
    public ResponseEntity<CreateFollowerResponseDto> createFollower(
            @RequestBody CreateFollowerRequestDto requestDto
    ) {
        CreateFollowerResponseDto responseDto = followerService
                .createFollower(
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

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateFollowerResponseDto> updateFollower(
            @PathVariable("id") Long id,
            @RequestBody UpdateFollowerRequestDto requestDto
    ) {
        UpdateFollowerResponseDto responseDto = followerService
                .updateFollower(
                        id,
                        requestDto.senderProfileId(),
                        requestDto.status()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}