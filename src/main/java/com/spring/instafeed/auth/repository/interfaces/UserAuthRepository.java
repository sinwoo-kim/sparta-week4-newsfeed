package com.spring.instafeed.auth.repository.interfaces;

import com.spring.instafeed.user.entity.User;

/**
 * 추후 확정성을 위한 인터페이스
 */
public interface UserAuthRepository {

    User registerUser(User user);

    User findByEmail(String email);

    boolean existsEmail(String email);
}
