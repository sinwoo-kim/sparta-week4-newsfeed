package com.spring.instafeed.profile.repository;

import com.spring.instafeed.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {


    /**
     * 닉네임이 이미 존재하는지 확인합니다.
     *
     * @param nickname : 확인할 닉네임
     * @return 닉네임이 존재하면 true, 그렇지 않으면 false
     */
    boolean existsByNickname(String nickname);

    // 특정 ID에 해당하는 삭제되지 않은 프로필 조회
    Optional<Profile> findByIdAndIsDeletedFalse(Long id);

    /**
     * 삭제되지 않은 모든 프로필을 조회합니다.
     *
     * @return 삭제되지 않은 프로필의 리스트
     */
    List<Profile> findAllByIsDeletedFalse();

    /**
     * 기능
     * 사용자의 Id가 작성했으며 삭제되지 않은 프로필 목록 조회
     *
     * @param userId : 프로필을 조회하려는 사용자의 Id
     * @return 삭제되지 않은 프로필들의 리스트
     */
    List<Profile> findAllByUserIdAndIsDeletedFalse(Long userId);

}