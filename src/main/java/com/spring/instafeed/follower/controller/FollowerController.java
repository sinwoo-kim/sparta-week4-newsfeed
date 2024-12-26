package com.spring.instafeed.follower.controller;


import com.spring.instafeed.follower.dto.request.*;
import com.spring.instafeed.follower.dto.response.*;
import com.spring.instafeed.follower.service.FollowerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * @param request : user 정보를 포함한 HttpServletRequest
     * @param requestDto : 상태를 포함한 UpdateFollowerRequestDto
     * @return UpdateFollowerResponseDto
     */
    @PatchMapping
    public ResponseEntity<UpdateFollowerResponseDto> updateFollower(
            HttpServletRequest request,
            @RequestBody UpdateFollowerRequestDto requestDto
    ) {
        Long id = (Long) request.getAttribute("userId");

        UpdateFollowerResponseDto responseDto = followerService
                .updateFollower(
                        id,
                        requestDto.status()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}