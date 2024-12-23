package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;

public record QueryProfileResponseDto(
    String nickname,
    String content,
    String imagePath
) {}