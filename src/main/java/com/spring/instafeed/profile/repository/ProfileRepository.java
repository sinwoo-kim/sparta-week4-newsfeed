package com.spring.instafeed.profile.repository;

import com.spring.instafeed.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    // 닉네임 중복 검사를 위한 메서드
    boolean existsByNickname(String nickname);

    // 특정 ID에 해당하는 삭제되지 않은 프로필을 조회하는 메소드
    Optional<Profile> findByIdAndIsDeletedFalse(Long id);

    // @Query를 사용하여 삭제되지 않은 모든 프로필을 조회하는 커스텀 쿼리 메소드
    @Query("SELECT p FROM Profile p WHERE p.isDeleted = false")
    List<Profile> findAllActiveProfiles();
}
