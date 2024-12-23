package com.spring.instafeed.user.repository;

import com.spring.instafeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 기능
     * 소프트 딜리트가 이루어진 식별자를 제외한 회원 정보 조회용 메서드
     *
     * @param id : 조회하려는 사용자의 식별자
     * @return Optional<User>
     */
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isDeleted IS NULL")
    Optional<User> findByIdExceptDeleted(Long id);

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 식별자
     * @return User
     */
    default User findByIdOrElseThrow(Long id) {
        return findByIdExceptDeleted(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "입력된 id가 존재하지 않습니다. 다시 입력해 주세요."));
    }

    @Transactional
    @Modifying
    @Query("UPDATE User m SET m.isDeleted = TRUE, m.deletedAt = CURRENT_TIMESTAMP WHERE m.id = :id")
    int softDeleteById(Long id);
}