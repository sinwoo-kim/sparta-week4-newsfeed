package com.spring.instafeed.profile.repository;

import com.spring.instafeed.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * 주어진 ID에 해당하는 프로필을 조회하고, 존재하지 않을 경우 예외를 발생시킵니다.
     *
     * @param id : 조회하려는 프로필의 식별자
     * @return 조회된 프로필
     * @throws ResponseStatusException : 프로필이 존재하지 않을 경우 404 Not Found 예외 발생
     */
    default Profile findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));
    }
}