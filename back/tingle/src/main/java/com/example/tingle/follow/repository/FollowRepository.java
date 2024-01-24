package com.example.tingle.follow.repository;

import com.example.tingle.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    List<Follow> findByUserEntityId(Long userId);

}
