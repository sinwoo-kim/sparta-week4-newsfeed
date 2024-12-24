package com.spring.instafeed.newsfeed.controller;

import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import com.spring.instafeed.newsfeed.dto.request.NewsfeedModifyRequestDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedCommonResponseDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedPageResponseDto;
import com.spring.instafeed.newsfeed.service.NewsfeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    /**
     * 게시물 생성 API
     *
     * @param createRequestDto
     * @return NewsfeedCommonResponseDto
     */
    @PostMapping
    public ResponseEntity<NewsfeedCommonResponseDto> createNewsfeedAPI(@RequestBody NewsfeedCreateRequestDto createRequestDto) {

        NewsfeedCommonResponseDto response = newsfeedService.createNewsfeed(createRequestDto);
        return new ResponseEntity<NewsfeedCommonResponseDto>((response), HttpStatus.CREATED);
    }

    // controller
    /**
     * 게시물 페이징 조회 API
     * 모든 게시물을 페이징 처리하여 조회하고 반환합니다.
     *
     * @param page 조회할 페이지 번호 (기본값: 0)
     * @param size 조회할 페이지 크기 (기본값: 10)
     * @return ResponseEntity<Page<NewsfeedResponseDto>> 페이징 처리된 게시물 목록
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<NewsfeedPageResponseDto>> findNewsfeedListAPI(
            @RequestParam(value = "page", defaultValue = "0") int page,  // 기본값은 첫 페이지
            @RequestParam(value = "size", defaultValue = "10") int size) {  // 기본값은 10개 항목
        try {
            // 페이징 정보 기반으로 게시물 조회
            Pageable pageable = PageRequest.of(page, size);
            Page<NewsfeedPageResponseDto> newsfeedsPage = newsfeedService.findNewsfeedListWithPaging(pageable);
            return new ResponseEntity<>(newsfeedsPage, HttpStatus.OK); // 200 OK 반환
        } catch (Exception e) {
            // 예외 발생 시 500 Internal Server Error 반환
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 게시물 단건 조회 API
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
     * @param NewsfeedId 삭제할 게시물의 ID (경로 변수로 전달)
     * @return ResponseEntity<String> 삭제 성공 메시지 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNewsfeedAPI(@PathVariable("id") Long NewsfeedId) {

        newsfeedService.deleteNewsfeed(NewsfeedId);
        return ResponseEntity.ok("Newsfeed deletion successful"); // api 명세 없으면 이런 문제 생김
    }
}
