package com.spring.instafeed.auth.domain;

import com.spring.instafeed.exception.invalid.InvalidEmailException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

@Getter
public class Email {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String emailText;

    private Email(String email) {
        this.emailText = email;
    }

    /**
     * 입력받은 email 을 검증 후 Email 객체 생성
     */
    public static Email generateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new InvalidEmailException(HttpStatus.BAD_REQUEST, "Email must not be empty");
        }

        if (!validateEmail(email)) {
            throw new InvalidEmailException(HttpStatus.BAD_REQUEST, "Email is not valid");
        }

        return new Email(email);
    }

    /**
     * 이메일 형식 검증
     */
    private static boolean validateEmail(String email) {
        return pattern.matcher(email).matches();
    }
}
