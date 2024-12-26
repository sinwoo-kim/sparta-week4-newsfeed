package com.spring.instafeed.auth.domain;

import com.spring.instafeed.auth.util.BCryptUtil;
import com.spring.instafeed.exception.invalid.InvalidPasswordException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

@Getter
public class Password {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    private final String encryptedPassword;

    private Password(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            throw new InvalidPasswordException(HttpStatus.BAD_REQUEST, "Password must not be null or empty");
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
     * 비밀번호 형식 검증
     */
    public static void validatePassword(String password) {
        if (!pattern.matcher(password).matches()) {
            throw new InvalidPasswordException(HttpStatus.BAD_REQUEST, "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character.");
        }
    }

    /**
     * 입력된 비밀번호가 저장된 비밀번호와 일치하는지 검증
     */
    public boolean matchPassword(String rawPassword) {
        return BCryptUtil.matches(rawPassword, encryptedPassword);
    }
}
