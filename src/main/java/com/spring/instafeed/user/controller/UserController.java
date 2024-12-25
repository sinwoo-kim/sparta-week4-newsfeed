package com.spring.instafeed.user.controller;

import com.spring.instafeed.user.dto.request.UpdateUserRequestDto;
import com.spring.instafeed.user.dto.response.UpdateUserResponseDto;
import com.spring.instafeed.user.dto.response.ReadUserResponseDto;
import com.spring.instafeed.user.service.UserServiceImpl;
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
     * @param id : 조회하려는 사용자의 senderProfileId
     * @return UserResponseDto, HttpStatus.OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReadUserResponseDto> findById(
            @PathVariable("id") Long id
    ) {
        ReadUserResponseDto responseDto = userService.findById(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 비밀번호 수정
     *
     * @param id         : 비밀번호를 수정하려는 사용자의 식별자
     * @param requestDto : UpdateUserRequestDto
     * @return UserResponseDto, HttpStatus.OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateUserResponseDto> updatePassword(
            @PathVariable("id") Long id,
            @RequestBody UpdateUserRequestDto requestDto
    ) {
        UpdateUserResponseDto responseDto = userService.updatePassword(
                id,
                requestDto.password()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 사용자 소프트 딜리트
     *
     * @param id : 삭제하려는 사용자의 senderProfileId
     * @return HttpStatus.OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long id
    ) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}