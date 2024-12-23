package com.spring.instafeed.profile.repository;

import com.spring.instafeed.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {


    /**
     * 기능
     * 소프트 딜리트가 이루어진 식별자를 제외한 회원 정보 조회용 메서드
     *
     * @param id : 조회하려는 프로필의 식별자
     * @return 조회된 프로필을 옵셔널로 처리한 것
     */
    @Query("SELECT p FROM Profile p WHERE p.id = :id AND p.isDeleted IS NULL")
    Optional<Profile> findByIdExceptDeleted(Long id);

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 프로필의 식별자
     * @return 조회된 프로필
     */
    default Profile findByIdOrElseThrow(Long id) {
        return findByIdExceptDeleted(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "입력된 id가 존재하지 않습니다. 다시 입력해 주세요."));
    }
}