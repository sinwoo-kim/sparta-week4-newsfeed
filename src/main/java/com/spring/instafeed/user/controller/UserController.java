package com.spring.instafeed.user.controller;


import com.spring.instafeed.user.dto.request.SignUpUserRequestDto;
import com.spring.instafeed.user.dto.request.UpdateUserRequestDto;
import com.spring.instafeed.user.dto.response.UpdateUserResponseDto;
import com.spring.instafeed.user.dto.response.UserResponseDto;
import com.spring.instafeed.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    /**
     * 기능
     * 회원가입
     *
     * @param requestDto : SignUpUserRequestDto
     * @return UserResponseDto, HttpStatus.CREATED
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpUserRequestDto requestDto) {
        UserResponseDto responseDto = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 id
     * @return UserResponseDto, HttpStatus.OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findById(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 비밀번호 수정
     *
     * @param id         : 비밀번호를 수정하려는 사용자의 식별자
     * @param requestDto : UpdateUserRequestDto
     * @return UserResponseDto, HttpStatus.OK // 수정 필요
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateUserResponseDto> updatePasswordById(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto requestDto
    ) {
        UpdateUserResponseDto responseDto = userService.updatePasswordById(id, requestDto.getPassword());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 사용자 소프트 딜리트
     *
     * @param id : 삭제하려는 사용자의 id
     * @return HttpStatus.OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}