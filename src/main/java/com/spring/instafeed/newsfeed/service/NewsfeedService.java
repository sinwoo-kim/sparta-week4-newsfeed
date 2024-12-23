package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import com.spring.instafeed.newsfeed.dto.request.NewsfeedModifyRequestDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedCommonResponseDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedListResponseDto;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * newsfeed 생성 메서드
     *
     * @param createRequestDto
     * @param userId
     * @return NewsfeedCommonResponseDto
     */
    @Transactional
    public NewsfeedCommonResponseDto createNewsfeed(NewsfeedCreateRequestDto createRequestDto, Long userId) {

        User foundUser = findUserByID(userId);

        Newsfeed newNewsfeed = Newsfeed.of(createRequestDto, foundUser);
        log.info("newNewsfeed = {}", newNewsfeed.getNickname());

        Newsfeed savedNewsfeed = newsfeedRepository.save(newNewsfeed);
        log.info("savedNewsfeed.getImagePath() = {}", savedNewsfeed.getNickname());

        return convertToDto(savedNewsfeed);
    }


    /**
     * newsfeed 목록 조회 메서드
     *
     * @return NewsfeedListResponseDto
     */
    public List<NewsfeedListResponseDto> findNewsfeedList() {
        List<Newsfeed> foundList = newsfeedRepository.findAll();
        log.info("foundList = {}", foundList);
        return foundList.stream().map(NewsfeedListResponseDto::new).collect(Collectors.toList());
    }

    /**
     * newsfeed 단건 조회 메서드
     * newsfeed 생성 시 주어진 Id를 매개변수로 게시물을 조회합니다.
     *
     * @param newsfeedId
     * @return NewsfeedCommonResponseDto
     */
    public NewsfeedCommonResponseDto findNewsfeed(Long newsfeedId) {
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);
        return convertToDto(foundNewsfeed);
    }

    /**
     * newsfeed 단건 수정 메서드
     *
     * @param newsfeedId
     * @param modifyRequestDto
     * @param userId
     * @return NewsfeedCommonResponseDto
     */
    public NewsfeedCommonResponseDto modifyNewsfeed(Long newsfeedId, NewsfeedModifyRequestDto modifyRequestDto, Long userId) {

        User foundUser = findUserByID(userId);
        log.info("foundUser = {}" + foundUser);
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);
        log.info("foundNewsfeed = {}" + foundNewsfeed);

        // TODO :: 코드 검토 필요
        // ---------------------------------------------------------------------------
        if (!foundUser.getId().equals(foundNewsfeed.getFoundUser().getId())) {
            throw new ValidationException("User Id가 일치하지 않습니다.");
        }
        // ---------------------------------------------------------------------------

        Newsfeed newNewsfeed = foundNewsfeed.updateNewsfeed(modifyRequestDto);

        Newsfeed savedNewesfeed = newsfeedRepository.save(newNewsfeed);

        return

                convertToDto(savedNewesfeed);
    }

    /**
     * newsfeed 단건 삭제 메서드
     *
     * @param newsfeedId
     * @param userId
     */
    public void deleteNewsfeed(Long newsfeedId, Long userId) {
        User foundUser = findUserByID(userId);
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);

        // TODO :: 코드 검토 필요
        // ---------------------------------------------------------------------------
        if (!foundUser.getId().equals(foundNewsfeed.getFoundUser().getId())) {
            throw new ValidationException("User Id가 일치하지 않습니다.");
        }

        newsfeedRepository.deleteById(foundNewsfeed.getNewsfeedId());
    }


    /* ----------------------------------------공통 메서드---------------------------------------------------*/

    /**
     * User Id 조회 (if false -> 예외 처리)
     *
     * @param UserId
     * @return User
     */
    private User findUserByID(Long UserId) {
        return userRepository.findById(UserId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user id를 찾을 수 없어요"));
    }

    /**
     * newsfeed id 조회 (if false -> 예외 처리)
     *
     * @param newsfeedId
     * @return Newsfeed
     */
//
    private Newsfeed findNewsfeedById(Long newsfeedId) {
        return newsfeedRepository.findById(newsfeedId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "newsfeed id를 찾을 수 없어요"));

    }

    /**
     * newsfeed -> Dto 변환
     *
     * @param newsfeed
     * @return NewsfeedCommonResponseDto
     */
    private NewsfeedCommonResponseDto convertToDto(Newsfeed newsfeed) {
        return NewsfeedCommonResponseDto.convertDto(newsfeed);
    }
}

