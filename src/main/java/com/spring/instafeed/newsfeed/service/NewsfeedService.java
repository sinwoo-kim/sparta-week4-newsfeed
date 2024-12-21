package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.User;
import com.spring.instafeed.UserRepository;
import com.spring.instafeed.newsfeed.dto.request.NewsfeedCreateRequestDto;
import com.spring.instafeed.newsfeed.dto.response.NewsfeedCommonResponseDto;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;
    private final UserRepository userRepository;

    @Transactional
    public NewsfeedCommonResponseDto createNewsfeed(NewsfeedCreateRequestDto createRequestDto, Long userId) {

        // User foundUser = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id를 찾을 수 없어요"));
        // -------------------------------------------
        // TODO :: 하드코딩 (수정해야 됨!!)
        User foundUser = new User();
        userRepository.save(foundUser);
        // --------------------------------------------

        Newsfeed newNewsfeed = Newsfeed.of(createRequestDto, foundUser);
        log.info("newNewsfeed = {}", newNewsfeed.getNickname());

        Newsfeed savedNewsfeed = newsfeedRepository.save(newNewsfeed);
        log.info("savedNewsfeed.getImagePath() = {}", savedNewsfeed.getNickname());

        return convertToDto(savedNewsfeed);
    }

    // newsfeed -> Dto 변환 공통 메서드
    private NewsfeedCommonResponseDto convertToDto(Newsfeed newsfeed) {
        return NewsfeedCommonResponseDto.convertDto(newsfeed);
    }
}
