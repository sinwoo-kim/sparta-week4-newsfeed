package com.spring.instafeed.user.controller;

import com.spring.instafeed.user.dto.request.DeleteUserRequestDto;
import com.spring.instafeed.user.dto.request.UpdateUserRequestDto;
import com.spring.instafeed.user.dto.response.UpdateUserResponseDto;
import com.spring.instafeed.user.dto.response.ReadUserResponseDto;
import com.spring.instafeed.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // 속성
    private final UserServiceImpl userService;

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param request : 조회하려는 user 의 정보를 포함한 HttpServletRequest
     * @return UserResponseDto, HttpStatus.OK
     */
    @GetMapping
    public ResponseEntity<ReadUserResponseDto> readUserById(
            HttpServletRequest request
    ) {
        Long id = (Long) request.getAttribute("userId");

        ReadUserResponseDto responseDto = userService
                .readUserById(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 비밀번호 수정
     *
     * @param request    : 비밀번호를 수정하려는 사용자의 식별자
     * @param requestDto : UpdateUserRequestDto
     * @return UserResponseDto, HttpStatus.OK
     */
    @PatchMapping
    public ResponseEntity<UpdateUserResponseDto> updatePassword(
            HttpServletRequest request,
            @RequestBody UpdateUserRequestDto requestDto
    ) {
        Long id = (Long) request.getAttribute("userId");

        UpdateUserResponseDto responseDto = userService
                .updatePassword(
                        id,
                        requestDto.oldPassword(),
                        requestDto.newPassword()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 사용자 소프트 딜리트
     *
     * @param request : 삭제하려는 사용자의 senderProfileId
     * @return HttpStatus.OK
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            HttpServletRequest request,
            @RequestBody DeleteUserRequestDto requestDto
    ) {
        Long id = (Long) request.getAttribute("userId");

        userService.deleteUser(
                id,
                requestDto.password()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}