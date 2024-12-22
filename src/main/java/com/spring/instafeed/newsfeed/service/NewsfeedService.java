package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedCommonResponseDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedListResponseDto;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;
    private final UserRepository userRepository;

    /**
     * 기능
     * newsfeed 생성
     *
     * @param createRequestDto
     * @param userId
     * @return NewsfeedCommonResponseDto
     */
    @Transactional
    public NewsfeedCommonResponseDto createNewsfeed(NewsfeedCreateRequestDto createRequestDto, Long userId) {

         User foundUser = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id를 찾을 수 없어요"));

        Newsfeed newNewsfeed = Newsfeed.of(createRequestDto, foundUser);
        log.info("newNewsfeed = {}", newNewsfeed.getNickname());

        Newsfeed savedNewsfeed = newsfeedRepository.save(newNewsfeed);
        log.info("savedNewsfeed.getImagePath() = {}", savedNewsfeed.getNickname());

        return convertToDto(savedNewsfeed);
    }


    /**
     * 기능
     * newsfeed 목록 조회
     *
     * @return NewsfeedListResponseDto
     */
    public List<NewsfeedListResponseDto> findNewsfeedList() {
        List<Newsfeed> foundList = newsfeedRepository.findAll();
        log.info("foundList = {}", foundList);
        return foundList.stream().map(NewsfeedListResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 기능
     * newsfeed 단건 조회
     * newsfeed 생성 시 주어진 Id를 매개변수로 게시물을 조회합니다.
     * @param newsfeedId
     * @return NewsfeedCommonResponseDto
     */
    public NewsfeedCommonResponseDto findNewsfeed(Long newsfeedId) {
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);
        return convertToDto(foundNewsfeed);
    }

    /**
     * 공통 기능
     * newsfeed id 조회
     *
     * @param newsfeedId
     * @return Newsfeed
     */
    //
    private Newsfeed findNewsfeedById(Long newsfeedId) {
        return newsfeedRepository.findById(newsfeedId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id를 찾을 수 없어요"));

    }

     /**
     * 공통 기능
     * newsfeed -> Dto 변환
      *
     * @param newsfeed
     * @return NewsfeedCommonResponseDto
     */
    private NewsfeedCommonResponseDto convertToDto(Newsfeed newsfeed) {
        return NewsfeedCommonResponseDto.convertDto(newsfeed);
    }

}
