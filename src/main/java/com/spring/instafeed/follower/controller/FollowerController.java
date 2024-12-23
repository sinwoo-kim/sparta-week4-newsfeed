package com.spring.instafeed.follower.controller;

import com.spring.instafeed.follower.service.FollowerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/followers")
public class FollowerController {
    private final FollowerServiceImpl followerService;

}
