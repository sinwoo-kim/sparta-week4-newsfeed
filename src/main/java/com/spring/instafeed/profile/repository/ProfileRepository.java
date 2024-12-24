package com.spring.instafeed.profile.repository;

import com.spring.instafeed.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * 닉네임이 이미 존재하는지 확인합니다.
     *
     * @param nickname : 확인할 닉네임
     * @return 닉네임이 존재하면 true, 그렇지 않으면 false
     */
    boolean existsByNickname(String nickname);

    // @Query를 사용하여 삭제되지 않은 모든 프로필을 조회하는 커스텀 쿼리 메소드
    @Query("SELECT p FROM Profile p WHERE p.isDeleted = false")
    List<Profile> findAllActiveProfiles();

    /**
     * 삭제되지 않은 프로필을 ID로 조회합니다.
     *
     * @param id : 조회하려는 프로필의 식별자
     * @return 삭제되지 않은 프로필이 존재하면 Optional로 반환, 없으면 빈 Optional 반환
     */
    Optional<Profile> findByIdAndIsDeletedFalse(Long id);

    /**
     * 삭제되지 않은 모든 프로필을 조회합니다.
     *
     * @return 삭제되지 않은 프로필의 리스트
     */
    List<Profile> findAllByIsDeletedFalse();
}

