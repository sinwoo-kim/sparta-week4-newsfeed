package com.spring.instafeed.newsfeed.controller;

import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import com.spring.instafeed.newsfeed.dto.request.NewsfeedModifyRequestDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedCommonResponseDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedListResponseDto;
import com.spring.instafeed.newsfeed.service.NewsfeedService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    /**
     * 게시물 생성 API
     *
     * 세션에서 사용자 정보를 가져와 본인 계정으로 게시물을 생성할 수 있습니다.
     *
     * @param createRequestDto 게시물 생성 요청 정보를 담고 있는 DTO
     * @param session
     * @return NewsfeedCommonResponseDto 게시물 정보를 반환하는 공통 DTO
     */
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


    /**
     * 게시물 목록 조회 API
     *
     * @return NewsfeedListResponseDto
     */
    @GetMapping
    public List<NewsfeedListResponseDto> findNewsfeedListAPI() {
        return newsfeedService.findNewsfeedList();
    }

    /**
     * 게시물 단건 조회 API
     *
     * 사용자로부터 조회 할 newsfeed Id를 받아 서비스 계층으로 전달, 조회 요청을 처리합니다.
     *
     * @param newsfeedId
     * @return NewsfeedCommonResponseDto 게시물 정보를 반환하는 공통 DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsfeedCommonResponseDto> findNewsfeedAPI(@PathVariable("id") Long newsfeedId) {
        NewsfeedCommonResponseDto response = newsfeedService.findNewsfeed(newsfeedId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 게시물 내용 수정 API
     *
     * 사용자로부터 Newfeed Id를 받아 해당 id의 게시물을 수정합니다.
     * 본인만 처리 가능하도록 세션을 활용해서 사용자 id를 가져와 서비스 계층에 전달, 수정 요청을 처리합니다.
     *
     * @param newsfeedId
     * @param modifyRequestDto
     * @param session
     * @return NewsfeedCommonResponseDto 게시물 정보를 반환하는 공통 DTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<NewsfeedCommonResponseDto> modifyNeewsfeedAPI(@PathVariable("id") Long newsfeedId,
                                                                        @RequestBody NewsfeedModifyRequestDto modifyRequestDto,
                                                                        HttpSession session) {
//      Long userId = (Long) session.getAttribute("userId");
        // -------------------------------------
        // TODO :: 하드코딩 ( 수정해야 됨!!!)
        Long userId = 1L; // 하드코딩
        // -------------------------------------
        NewsfeedCommonResponseDto response = newsfeedService.modifyNewsfeed(newsfeedId, modifyRequestDto, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * 게시물 단건 삭제 API
     *
     * 사용자로부터 Newsfeed Id를 받아 해당 id의 게시물을 삭제합니다.
     * 본인만 처리 가능하도록 세션을 활용해서 사용자 id를 가져와 서비스 계층에 전달, 삭제 요청을 처리합니다.
     *
     * @param NewsfeedId
     * @param session
     * @return String
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNewsfeedAPI(@PathVariable("id") Long NewsfeedId,
                                                    HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
        // -------------------------------------
        // TODO :: 하드코딩 ( 수정해야 됨!!!)
        Long userId = 1L; // 하드코딩
        // -------------------------------------
        newsfeedService.deleteNewsfeed(NewsfeedId, userId);
        return ResponseEntity.ok("Newsfeed deletion successful");
    }
}
