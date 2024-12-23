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
import org.springframework.web.server.ResponseStatusException;

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
     * <p>TODO:</p>
     * * - 현재 사용자 ID가 하드코딩되어 있음
     *
     * @param createRequestDto 게시물 생성 요청 정보를 담고 있는 DTO
     * @return NewsfeedCommonResponseDto 게시물 정보를 반환하는 공통 DTO
     */
    @PostMapping
    public ResponseEntity<NewsfeedCommonResponseDto> createNewsfeedAPI(@RequestBody NewsfeedCreateRequestDto createRequestDto) {

        NewsfeedCommonResponseDto response = newsfeedService.createNewsfeed(createRequestDto);
        return new ResponseEntity<NewsfeedCommonResponseDto>((response), HttpStatus.CREATED);
    }

    /**
     * 게시물 목록 조회 API
     *
     * 게시물 전체 목록을 조회합니다.
     *
     * @return NewsfeedListResponseDto
     */
    @GetMapping
    public ResponseEntity<NewsfeedListResponseDto> findNewsfeedListAPI() { //깡통 x 코드 컨벤션 깨짐
        NewsfeedListResponseDto response = newsfeedService.findNewsfeedList();
        return new ResponseEntity<NewsfeedListResponseDto>(response, HttpStatus.CREATED);
    }

    /**
     * 게시물 단건 조회 API
     *
     * 사용자로부터 조회 할 newsfeed Id를 받아 서비스 계층으로 전달, 조회 요청을 처리합니다.
     *
     * @param newsfeedId 조회할 게시물의 ID (경로 변수로 전달)
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
     * <p>TODO:</p>
     * * - 현재 사용자 ID가 하드코딩되어 있음
     *
     * @param newsfeedId 수정할 게시물의 ID (경로 변수로 전달)
     * @param modifyRequestDto 게시물 수정 요청 정보를 담고 있는 DTO
     * @return NewsfeedCommonResponseDto 게시물 정보를 반환하는 공통 DTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<NewsfeedCommonResponseDto> modifyNewsfeedAPI(
            @PathVariable("id") Long newsfeedId,
            @RequestBody NewsfeedModifyRequestDto modifyRequestDto
    ) {
        NewsfeedCommonResponseDto response = newsfeedService.modifyNewsfeed(newsfeedId, modifyRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * 게시물 단건 삭제 API
     *
     * 사용자로부터 Newsfeed Id를 받아 해당 id의 게시물을 삭제합니다.
     * 본인만 처리 가능하도록 세션을 활용해서 사용자 id를 가져와 서비스 계층에 전달, 삭제 요청을 처리합니다.
     *
     * <p>TODO:</p>
     *  * - 현재 사용자 ID가 하드코딩되어 있음
     *
     * @param NewsfeedId 삭제할 게시물의 ID (경로 변수로 전달)
     * @return ResponseEntity<String> 삭제 성공 메시지 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNewsfeedAPI(@PathVariable("id") Long NewsfeedId) {
//
        newsfeedService.deleteNewsfeed(NewsfeedId);
        return ResponseEntity.ok("Newsfeed deletion successful"); // api 명세 없으면 이런 문제 생김
    }
}
