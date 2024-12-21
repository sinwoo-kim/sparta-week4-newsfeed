package com.spring.instafeed.repository;

import com.spring.instafeed.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}

