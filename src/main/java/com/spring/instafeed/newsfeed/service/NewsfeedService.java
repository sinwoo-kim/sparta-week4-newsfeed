package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import com.spring.instafeed.newsfeed.dto.request.NewsfeedModifyRequestDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedCommonResponseDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedListResponseDto;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import com.spring.instafeed.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // read-only로 적용하면 빠질 일 없음, setter는 쓰기 전용
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    /**
     * 게시물 생성 메서드
     * <p>
     * 요청된 데이터를 기반으로 새로운 게시물을 생성하고 저장합니다.
     * newNewsfeed 객체에 저장된 foundProfile 객체을 활용하여 닉네임을 가져옵니다.
     * <p>TODO</p>
     * - 코드 검토 필요
     *
     * @param createRequestDto 게시물 생성 요청 정보를 담은 DTO
     * @return NewsfeedCommonResponseDto 게시물 정보를 담은 공통 DTO
     */
    @Transactional
    public NewsfeedCommonResponseDto createNewsfeed(NewsfeedCreateRequestDto createRequestDto) {
        Long profileId = createRequestDto.profileId();
        Profile foundProfile = profileRepository.findById(profileId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile id를 찾을 수 없어요"));
        Newsfeed newNewsfeed = Newsfeed.of(createRequestDto, foundProfile);
        Newsfeed savedNewsfeed = newsfeedRepository.save(newNewsfeed);
        return NewsfeedCommonResponseDto.convertToDto(savedNewsfeed);
    }


    /**
     * 게시물 목록 조회 메서드
     * <p>
     * 데이터베이스에서 모든 게시물을 조회하고, DTO로 변환하여 반환합니다.
     *
     * @return NewsfeedListResponseDto 게시물 목록을 담은 DTO
     */
    public NewsfeedListResponseDto findNewsfeedList() {

        List<Newsfeed> foundList = newsfeedRepository.findAll();

        // 게시물 목록 DTO로 변환
        List<NewsfeedListResponseDto.NewsfeedDto> NewsfeedDtoList = foundList.stream()
                .map(NewsfeedListResponseDto.NewsfeedDto::createFrom)// DTO 변환
                .toList(); // 리스트로 수집

        log.info("NewsfeedDtoList = {}", NewsfeedDtoList);
        // 최종 DTO 반환
        return NewsfeedListResponseDto.createFrom(NewsfeedDtoList);
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
        return NewsfeedCommonResponseDto.convertToDto(foundNewsfeed);
    }

    /**
     * 게시물 단건 수정 메서드
     * <p>
     * 사용자 ID와 게시물 ID를 기반으로 작성자 검증 후, 요청된 수정 내용을 반영합니다.
     * 수정된 게시물 정보는 데이터베이스에 저장되며, 공통 DTO로 반환됩니다.
     *
     * <p>TODO</p>
     * - 작성자 검증 로직 코드 검토 필요합니다.
     * - 예외 공통 처리 미구현
     *
     * @param newsfeedId
     * @param modifyRequestDto
     * @return NewsfeedCommonResponseDto 게시물 정보를 담은 공통 DTO
     */
    @Transactional
    public NewsfeedCommonResponseDto modifyNewsfeed(Long newsfeedId, NewsfeedModifyRequestDto modifyRequestDto) {
        // 게시물 조회
        Newsfeed foundNewsfeed = findNewsfeedById(newsfeedId);

        // 수정 요청 데이터 반영
        foundNewsfeed.updateNewsfeed(modifyRequestDto);

        // 데이터베이스에 저장
        Newsfeed updatedNewsfeed = newsfeedRepository.save(foundNewsfeed);

        // 수정된 데이터를 DTO로 변환하여 반환
        return NewsfeedCommonResponseDto.convertToDto(updatedNewsfeed);
    }

    /**
     * 게시물 단건 삭제 메서드
     * <p>
     * 게시물 작성자 본인만 삭제할 수 있도록 ID 검증을 수행하며,
     * 검증 완료 후 데이터베이스에서 해당 게시물을 삭제합니다.
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

        newsfeedRepository.deleteById(foundNewsfeed.getNewsfeedId());
    }


    /* ----------------------------------------공통 메서드---------------------------------------------------*/

    /**
     * newsfeed Id 조회 (if false -> 예외 처리)
     *
     * @param newsfeedId
     * @return Newsfeed
     */
// 안뺴는게 더 좋을 수 있음. 잘 읽히는게 중요
    private Newsfeed findNewsfeedById(Long newsfeedId) {
        return newsfeedRepository.findById(newsfeedId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "newsfeed id를 찾을 수 없어요"));

    }
}

