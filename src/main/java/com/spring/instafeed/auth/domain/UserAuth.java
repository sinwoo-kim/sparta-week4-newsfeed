package com.spring.instafeed.auth.domain;

import lombok.Getter;

@Getter
public class UserAuth {

    private final Email email;
    private final Password password;
    private Long userId;

    public UserAuth(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("invalid auth information");
        }
        this.email = Email.generateEmail(email);
        this.password = Password.generateEncryptedPassword(password);
    }

    public UserAuth(Long userId, String email, String password) {
        this.userId = userId;
        this.email = Email.generateEmail(email);
        this.password = Password.generatePassword(password);
    }

    public String getEmail() {
        return email.getEmailText();
    }

    public String getPassword() {
        return password.getEncryptedPassword();
    }

    public boolean matchPassword(String rawPassword) {
        return password.matchPassword(rawPassword);
    }
}
