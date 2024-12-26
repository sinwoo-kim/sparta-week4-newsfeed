package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.exception.data.DataNotFoundException;
import com.spring.instafeed.exception.data.DataAlreadyDeletedException;
import com.spring.instafeed.newsfeed.dto.response.*;
import com.spring.instafeed.newsfeed.dto.response.ContentsWrapperResponseDto;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsfeedServiceImpl implements NewsfeedService {

    // 속성
    private final NewsfeedRepository newsfeedRepository;
    private final ProfileRepository profileRepository;

    /**
     * 기능
     * 뉴스피드 생성
     *
     * @param profileId : 게시물을 작성하는 사용자의 프로필 ID
     * @param content   : 게시물의 내용
     * @param imagePath : 게시물에 첨부된 이미지 경로
     * @return CreateNewsfeedResponseDto
     */
    @Transactional
    @Override
    public CreateNewsfeedResponseDto createNewsfeed(
            Long profileId,
            String content,
            String imagePath
    ) {
        Profile foundProfile = profileRepository
                .findByIdAndIsDeletedFalse(profileId)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        Newsfeed newsfeedToSave = Newsfeed.create(
                foundProfile,
                content,
                imagePath
        );
        Newsfeed savedNewsfeed = newsfeedRepository
                .save(newsfeedToSave);

        return CreateNewsfeedResponseDto.toDto(savedNewsfeed);
    }

    /**
     * 기능
     * 게시물 목록 페이징 조회
     *
     * @param pageable 페이징 정보 (페이지 번호, 크기 등)
     * @return List<ReadNewsfeedResponseDto> 일정 페이징 응답 DTO
     */
    @Override
    public ContentsWrapperResponseDto readAllNewsfeeds(Pageable pageable) {
        Page<Newsfeed> allNewsfeeds = newsfeedRepository
                .findAllByIsDeletedFalseOrderByUpdatedAtDesc(pageable);

        // Newsfeed를 DTO로 변환
        List<ReadNewsfeedResponseDto> newsfeedList = allNewsfeeds
                .getContent()
                .stream()
                .map(ReadNewsfeedResponseDto::toDto)
                .toList();

        // Wrapper DTO로 반환
        return new ContentsWrapperResponseDto(newsfeedList);
    }
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

    /**
     * 기능
     * 게시물 단건 조회
     *
     * @param id : 조회하려는 게시물의 식별자
     * @return ReadNewsfeedResponseDto
     */
    @Override
    public ReadNewsfeedResponseDto readNewsfeedById(Long id) {
        Newsfeed foundNewsfeed = newsfeedRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        return ReadNewsfeedResponseDto.toDto(foundNewsfeed);
    }

    /**
     * 기능
     * 게시물 단건 수정 (내용만, 인스타그램은 이미지 수정 기능이 없으므로)
     *
     * @param id      : 수정하려는 게시물의 식별자
     * @param content : 수정하려는 게시물의 내용
     * @return UpdateNewsfeedResponseDt
     */
    @Transactional
    @Override
    public UpdateNewsfeedResponseDto updateNewsfeed(
            Long id,
            String content
    ) {
        Newsfeed foundNewsfeed = newsfeedRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        foundNewsfeed.update(content);

        return UpdateNewsfeedResponseDto.toDto(foundNewsfeed);
    }

    /**
     * 기능
     * 게시물 단건 삭제
     *
     * @param id : 삭제하려는 게시물의 식별자
     */
    @Transactional
    @Override
    public void deleteNewsfeed(Long id) {
        Newsfeed foundNewsfeed = newsfeedRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        // 이미 삭제된 게시물인지 확인
        if (foundNewsfeed.getIsDeleted()) {
            throw new DataAlreadyDeletedException(
                    HttpStatus.CONFLICT,
                    "The requested data has already been deleted"
            );
        }

        // 게시물 삭제 처리
        foundNewsfeed.markAsDeleted();
    }
}