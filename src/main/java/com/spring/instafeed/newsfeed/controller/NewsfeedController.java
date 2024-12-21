package com.spring.instafeed.newsfeed.controller;

import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedCommonResponseDto;
import com.spring.instafeed.newsfeed.service.NewsfeedService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    @PostMapping
    public ResponseEntity<NewsfeedCommonResponseDto> createNewsfeedAPI(
            @RequestBody NewsfeedCreateRequestDto createRequestDto, HttpSession session) {

//        Long userId = session.getAttribute("userId");
        // -------------------------------------
        // TODO :: 하드코딩 ( 수정해야 됨!!!)
        Long userId = 1L; // 하드코딩
        // -------------------------------------
        NewsfeedCommonResponseDto response = newsfeedService.createNewsfeed(createRequestDto, userId);
        log.info("response = {}", response);
        return new ResponseEntity<NewsfeedCommonResponseDto>((response), HttpStatus.CREATED);
    }

    // 게시물 목록 조회
    // 게시물 단건 조회
    // 게시물 내용 수정
    // 게시물 단건 삭제
}
