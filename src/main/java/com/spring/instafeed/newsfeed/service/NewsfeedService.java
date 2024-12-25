package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.newsfeed.dto.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsfeedService {

    CreateNewsfeedResponseDto createNewsfeed(
            Long profileId,
            String content,
            String imagePath
    );

    List<ReadNewsfeedResponseDto> readAllNewsfeeds(Pageable pageable);

    ReadNewsfeedResponseDto readNewsfeedById(Long id);

    UpdateNewsfeedResponseDto updateNewsfeed(
            Long id,
            String content
    );

    void deleteNewsfeed(Long id);
}