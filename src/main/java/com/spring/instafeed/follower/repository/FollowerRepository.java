package com.spring.instafeed.follower.repository;

import com.spring.instafeed.follower.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    @Query("SELECT f FROM Follower f WHERE f.deletedAt IS NULL")
    List<Follower> findAllExceptDeleted();
}