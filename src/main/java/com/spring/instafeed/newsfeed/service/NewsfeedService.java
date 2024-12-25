package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.newsfeed.dto.request.UpdateNewsfeedRequestDto;
import com.spring.instafeed.newsfeed.dto.response.*;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;
    private final ProfileRepository profileRepository;

    /**
     * 기능
     * 뉴스피드 생성
     *
     * @param profileId : 게시물을 작성하는 사용자의 프로필 ID
     * @param content   : 게시물의 내용
     * @param imagePath : 게시물에 첨부된 이미지 경로
     * @return CreateNewsfeedResponseDto : 생성된 뉴스피드를 DTO 형태로 반환
     */
    @Transactional
    public CreateNewsfeedResponseDto createNewsfeed(
            Long profileId,
            String content,
            String imagePath
    ) {
        Profile foundProfile = profileRepository
                .findById(profileId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        Newsfeed newsfeedToSave = Newsfeed.create(
                foundProfile,
                content,
                imagePath
        );

        Newsfeed savedNewsfeed = newsfeedRepository.save(newsfeedToSave);

        return CreateNewsfeedResponseDto.toDto(savedNewsfeed);
    }

    /**
     * 기능
     * 게시물 목록 페이징 조회
     *
     * @param pageable 페이징 정보 (페이지 번호, 크기 등)
     * @return List<ReadNewsfeedResponseDto> 일정 페이징 응답 DTO
     */
    public List<ReadNewsfeedResponseDto> readAllNewsfeeds(Pageable pageable) {

        Page<Newsfeed> allNewsfeeds = newsfeedRepository
                .findAllByIsDeletedFalseOrderByUpdatedAtDesc(pageable);

        List<ReadNewsfeedResponseDto> newsfeedList = new ArrayList<>();

        newsfeedList = allNewsfeeds
                .getContent()
                .stream()
                .map(ReadNewsfeedResponseDto::toDto)
                .toList();

        return newsfeedList;

        /*
        [람다 반영 전 코드]
        newsfeedList = allNewsfeeds
        .getContent()
        .stream()
        .map(newsfeed -> new ReadNewsfeedResponseDto(
                newsfeed.getId(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getContent(),
                newsfeed.getImagePath()
        ))
        .toList();
         */
    }

    /**
     * 게시물 단건 조회 메서드.
     *
     * @param newsfeedId
     * @return NewsfeedCommonResponseDto
     */
    public NewsfeedResponseDto findNewsfeed(Long newsfeedId) {
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);
        return NewsfeedResponseDto.toDto(foundNewsfeed);
    }

    /**
     * 게시물 단건 수정 메서드
     *
     * <p>TODO</p>
     * - 작성자 검증 로직 코드 검토 필요합니다.
     * - 예외 공통 처리 미구현
     *
     * @param newsfeedId
     * @param modifyRequestDto
     * @return NewsfeedCommonResponseDto
     */
    @Transactional
    public NewsfeedResponseDto updateNewsfeed(Long newsfeedId, UpdateNewsfeedRequestDto modifyRequestDto) {
        // 게시물 조회
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);

        // 수정 요청 데이터 반영
        foundNewsfeed.updateNewsfeed(modifyRequestDto);

        // 데이터베이스에 저장
        Newsfeed updatedNewsfeed = newsfeedRepository.save(foundNewsfeed);

        // 수정된 데이터를 DTO로 변환하여 반환
        return NewsfeedResponseDto.toDto(updatedNewsfeed);
    }

    /**
     * 게시물 단건 삭제 메서드
     *
     * <p>TODO</p>
     * - 예외 공통 처리 미구현
     * - isDeleted , DeleteAt 어떻게 적용해야되는지?
     *
     * @param newsfeedId
     */
    @Transactional
    public void deleteNewsfeed(Long newsfeedId) {
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);

        newsfeedRepository.deleteById(foundNewsfeed.getId());
    }


    /* ----------------------------------------공통 메서드---------------------------------------------------*/

    /**
     * newsfeed Id 조회 (if false -> 예외 처리)
     *
     * @param newsfeedId
     * @return Newsfeed
     */

    private Newsfeed findNewsfeedById(Long newsfeedId) {
        return newsfeedRepository.findById(newsfeedId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "newsfeed id를 찾을 수 없어요"));

    }
}

