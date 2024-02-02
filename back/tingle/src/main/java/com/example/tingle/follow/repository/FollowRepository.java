package com.example.tingle.follow.repository;

import com.example.tingle.follow.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Integer> {

    List<FollowEntity> findByUserEntityId(Long userId) throws SQLException;

    @Query("SELECT f FROM FollowEntity f WHERE f.userEntity.id = :userId AND f.starEntity.id = :starId")
    FollowEntity findByUserEntityIdAndStarEntityId(Long userId, Long starId) throws SQLException;


}
