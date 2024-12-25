package com.spring.instafeed.newsfeed.repository;

import com.spring.instafeed.newsfeed.entity.Newsfeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {

    /**
     * 모든 게시물 데이터를 수정일 기준 내림차순으로 페이징 처리
     *
     * @param pageable 페이징 정보
     * @return Page<Newsfeed> 페이징 처리된 게시물 데이터
     */
    Page<Newsfeed> findAllByIsDeletedFalseOrderByUpdatedAtDesc(Pageable pageable);
}