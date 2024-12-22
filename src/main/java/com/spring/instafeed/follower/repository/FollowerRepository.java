package com.spring.instafeed.follower.repository;

import com.spring.instafeed.follower.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
}
