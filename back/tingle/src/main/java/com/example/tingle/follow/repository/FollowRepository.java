package com.example.tingle.follow.repository;

import com.example.tingle.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer>, CustomFollowRepository {
}
