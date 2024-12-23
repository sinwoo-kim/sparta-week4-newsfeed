package com.spring.instafeed.follower.controller;

import com.spring.instafeed.follower.dto.request.CreateFollowerRequestDto;
import com.spring.instafeed.follower.dto.response.FollowerResponseDto;
import com.spring.instafeed.follower.service.FollowerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/followers")
public class FollowerController {
    private final FollowerServiceImpl followerService;

    @PostMapping
    public ResponseEntity<FollowerResponseDto> sendFollowRequest(@RequestBody CreateFollowerRequestDto requestDto) {

        FollowerResponseDto responseDto = followerService.sendFollowRequest(requestDto.getSenderId(), requestDto.getReceiverId());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
