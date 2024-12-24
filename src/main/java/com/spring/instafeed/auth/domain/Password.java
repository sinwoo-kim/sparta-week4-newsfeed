package com.spring.instafeed.auth.domain;

import com.spring.instafeed.auth.util.BCryptUtil;
import lombok.Getter;

@Getter
public class Password {

    private final String encryptedPassword;

    private Password(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or empty");
        }
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * 입력받은 비밀번호를 암호화 후 Password 객체 생성
     */
    public static Password generateEncryptedPassword(String rawPassword) {
        return new Password(BCryptUtil.encrypt(rawPassword));
    }

    /**
     * 이미 암호화된 비밀번호로 Password 객체 생성
     */
    public static Password generatePassword(String encryptedPassword) {
        return new Password(encryptedPassword);
    }

    /**
     * 입력된 비밀번호가 저장된 비밀번호와 일치하는지 검증
     */
    public boolean matchPassword(String rawPassword) {
        return BCryptUtil.matches(rawPassword, encryptedPassword);
    }
}
