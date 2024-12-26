package com.spring.instafeed.auth.repository;

import com.spring.instafeed.auth.repository.interfaces.UserAuthRepository;
import com.spring.instafeed.auth.repository.jpa.JpaUserAuthRepository;
import com.spring.instafeed.exception.data.DataNotFoundException;
import com.spring.instafeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final JpaUserAuthRepository jpaUserAuthRepository;

    @Override
    @Transactional
    public User registerUser(User user) {
        return jpaUserAuthRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return jpaUserAuthRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException(HttpStatus.NOT_FOUND,"Entity Not Found"));
    }

    @Override
    public boolean existsEmail(String email) {
        return jpaUserAuthRepository.existsByEmail(email);
    }
}
