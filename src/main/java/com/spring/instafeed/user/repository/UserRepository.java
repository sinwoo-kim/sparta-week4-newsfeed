package com.spring.instafeed.user.repository;

import com.spring.instafeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 식별자
     */
    Optional<User> findByIdAndDeletedAtIsNull(Long id);
}