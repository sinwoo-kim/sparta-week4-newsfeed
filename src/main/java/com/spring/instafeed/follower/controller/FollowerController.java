package com.spring.instafeed.follower.controller;

import com.spring.instafeed.follower.dto.request.CreateFollowerRequestDto;
import com.spring.instafeed.follower.dto.request.UpdateFollowerRequestDto;
import com.spring.instafeed.follower.dto.response.FollowerResponseDto;
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
    private final FollowerServiceImpl followerService;

    @PostMapping
    public ResponseEntity<FollowerResponseDto> sendFollowRequest(@RequestBody CreateFollowerRequestDto requestDto) {

        FollowerResponseDto responseDto = followerService.sendFollowRequest(requestDto.getSenderId(), requestDto.getReceiverId());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FollowerResponseDto>> readAllFollowers() {

        List<FollowerResponseDto> allFollowers = new ArrayList<>();

        allFollowers = followerService.findAll();

        return new ResponseEntity<>(allFollowers, HttpStatus.OK);
    }

    /**
     * @param id         : 요청을 받아서 상태를 변경하려는 프로필의 id
     * @param requestDto UpdateFollowerRequestDto
     * @return UpdateFollowerResponseDto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateFollowerResponseDto> updateFollowingStatus(
            @PathVariable Long id,
            @RequestBody UpdateFollowerRequestDto requestDto
    ) {
        UpdateFollowerResponseDto responseDto = followerService.updateFollowingStatus(
                id,
                requestDto.getRequestSenderId(),
                requestDto.getStatus()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}