package com.spring.instafeed.follower.repository;

import com.spring.instafeed.follower.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    List<Follower> findAllByStatusIsNotNull();

    Optional<Follower> findBySenderProfileIdAndReceiverProfileId(
            Long senderProfileId,
            Long receiverProfileId
    );
}