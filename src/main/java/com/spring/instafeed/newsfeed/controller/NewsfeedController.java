package com.spring.instafeed.newsfeed.controller;

import com.spring.instafeed.newsfeed.dto.request.CreateNewsfeedRequestDto;
import com.spring.instafeed.newsfeed.dto.request.UpdateNewsfeedRequestDto;
import com.spring.instafeed.newsfeed.dto.response.*;
import com.spring.instafeed.newsfeed.service.NewsfeedServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class NewsfeedController {

    // 속성
    private final NewsfeedServiceImpl newsfeedService;

    /**
     * 기능
     * 게시물 생성
     *
     * @param requestDto : 게시물 생성 요청에 해당하는 Request DTO
     * @return NewsfeedResponseDto
     */
    @PostMapping
    public ResponseEntity<CreateNewsfeedResponseDto> createNewsfeed(
            @RequestBody CreateNewsfeedRequestDto requestDto
    ) {
        CreateNewsfeedResponseDto responseDto = newsfeedService
                .createNewsfeed(
                        requestDto.profileId(),
                        requestDto.content(),
                        requestDto.imagePath()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 기능
     * 게시물 목록을 페이징 조회
     *
     * @param page 조회할 페이지 번호 (기본값: 0)
     * @param size 조회할 페이지 크기 (기본값: 10)
     * @return ResponseEntity<Page < NewsfeedResponseDto>> 페이징 처리된 게시물 목록
     */
    @GetMapping
    public ResponseEntity<List<ReadNewsfeedResponseDto>> readAllNewsfeeds(
            @RequestParam(value = "page", defaultValue = "0")
            int page,  // 기본값은 첫 페이지
            @RequestParam(value = "size", defaultValue = "1")
            int size // 기본값은 10개 항목
    ) {
        page = Math.max(page - 1, 0);
        // 사용자가 1 입력한다는 점 반영 및 음수가 되지 않도록 최솟값 설정
        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>(newsfeedService.readAllNewsfeeds(pageable), HttpStatus.OK);
    }

    /**
     * 기능
     * 게시물 단건 조회
     *
     * @param id 조회할 게시물의 ID (경로 변수로 전달)
     * @return NewsfeedCommonResponseDto 게시물 정보를 반환하는 공통 DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReadNewsfeedResponseDto> readNewsfeedById(
            @PathVariable("id") Long id
    ) {
        ReadNewsfeedResponseDto responseDto = newsfeedService
                .readNewsfeedById(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 게시물 내용 수정
     *
     * @param id         수정할 게시물의 ID (경로 변수로 전달)
     * @param requestDto 게시물 수정 요청 정보를 담고 있는 DTO
     * @return NewsfeedCommonResponseDto 게시물 정보 반환 DTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateNewsfeedResponseDto> updateNewsfeed(
            @PathVariable("id") Long id,
            @RequestBody UpdateNewsfeedRequestDto requestDto
    ) {
        UpdateNewsfeedResponseDto responseDto = newsfeedService
                .updateNewsfeed(
                        id,
                        requestDto.content()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 게시물 단건 삭제
     *
     * @param id : 삭제하려는 게시물의 식별자
     * @return : 상태 코드 메시지(200 OK)만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewsfeed(
            @PathVariable("id") Long id
    ) {
        newsfeedService.deleteNewsfeed(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}