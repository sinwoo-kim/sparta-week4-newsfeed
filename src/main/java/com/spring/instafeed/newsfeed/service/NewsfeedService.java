package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import com.spring.instafeed.newsfeed.dto.request.NewsfeedModifyRequestDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedCommonResponseDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedListResponseDto;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
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
    private final ProfileRepository profileRepository;

    /**
     * 게시물 생성 메서드
     * <p>
     * 요청된 데이터를 기반으로 새로운 게시물을 생성하고 저장합니다.
     *
     * <p>TODO</p>
     * - 코드 검토 필요
     *
     * @param createRequestDto 게시물 생성 요청 정보를 담은 DTO
     * @param userId           세션 정보에서 제공된 사용자 ID
     * @return NewsfeedCommonResponseDto 게시물 정보를 담은 공통 DTO
     */
    @Transactional
    public NewsfeedCommonResponseDto createNewsfeed(NewsfeedCreateRequestDto createRequestDto, Long userId) {
        // userId 존재 유무 조회
        User foundUser = findUserByID(userId);
        // 프로필 테이블에서 해당 사용자에 대한 프로필 ID를 조회.
        Profile foundProfile = findProfileById(foundUser.getId());
        // 객체 생성
        Newsfeed newNewsfeed = Newsfeed.of(createRequestDto, foundProfile);
        log.info("newNewsfeed = {}", newNewsfeed.getNickname());
        // DB 저장
        Newsfeed savedNewsfeed = newsfeedRepository.save(newNewsfeed);
        log.info("savedNewsfeed.getImagePath() = {}", savedNewsfeed.getNickname());

        return convertToDto(savedNewsfeed);
    }


    /**
     * 게시물 목록 조회 메서드
     * <p>
     * List를 활용해 게시물 목록을 조회합니다.
     *
     * @return NewsfeedListResponseDto 게시물 목록을 담은 DTO
     */
    public List<NewsfeedListResponseDto> findNewsfeedList() {
        List<Newsfeed> foundList = newsfeedRepository.findAll();
        log.info("foundList = {}", foundList);
        return foundList.stream().map(NewsfeedListResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 게시물 단건 조회 메서드
     * <p>
     * newsfeed 생성 시 주어진 Id를 매개변수로 게시물을 조회합니다.
     *
     * @param newsfeedId
     * @return NewsfeedCommonResponseDto 게시물 정보를 담은 공통 DTO
     */
    public NewsfeedCommonResponseDto findNewsfeed(Long newsfeedId) {
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);
        return convertToDto(foundNewsfeed);
    }

    /**
     * 게시물 단건 수정 메서드
     *
     * 사용자 ID와 게시물 ID를 기반으로 작성자 검증 후, 요청된 수정 내용을 반영합니다.
     * 수정된 게시물 정보는 데이터베이스에 저장되며, 공통 DTO로 반환됩니다.
     *
     * <p>TODO</p>
     * - 작성자 검증 로직 코드 검토 필요합니다.
     * - 예외 공통 처리 미구현
     *
     * @param newsfeedId
     * @param modifyRequestDto
     * @param userId
     * @return NewsfeedCommonResponseDto 게시물 정보를 담은 공통 DTO
     */
    public NewsfeedCommonResponseDto modifyNewsfeed(Long newsfeedId, NewsfeedModifyRequestDto modifyRequestDto, Long userId) {

        User foundUser = findUserByID(userId);
        Profile foundProfile = findProfileById(foundUser.getId());
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);

        Newsfeed newNewsfeed = foundNewsfeed.updateNewsfeed(modifyRequestDto);

        Newsfeed savedNewesfeed = newsfeedRepository.save(newNewsfeed);

        return convertToDto(savedNewesfeed);
    }

    /**
     * 게시물 단건 삭제 메서드
     *
     * 게시물 작성자 본인만 삭제할 수 있도록 ID 검증을 수행하며,
     * 검증 완료 후 데이터베이스에서 해당 게시물을 삭제합니다.
     *
     * <p>TODO</p>
     * - 예외 공통 처리 미구현
     * - isDeleted , DeleteAt 어떻게 적용해야되는지?
     *
     * @param newsfeedId
     * @param userId
     */
    public void deleteNewsfeed(Long newsfeedId, Long userId) {
        User foundUser = findUserByID(userId);
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);

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
     * Profile Id 조회 (if false -> 예외 처리)
     *
     */
    private Profile findProfileById(Long foundUser) {
        return profileRepository.findById(foundUser).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile id를 찾을 수 없어요"));
    }


    /**
     * newsfeed Id 조회 (if false -> 예외 처리)
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

