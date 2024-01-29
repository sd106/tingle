package com.example.tingle.follow.repository;

import com.example.tingle.follow.entity.Follow;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    List<Follow> findByUserEntityId(Long userId) throws SQLException;

    @Query("SELECT f FROM Follow f WHERE f.userEntity.id = :userId AND f.starEntity.id = :starId")
    Follow findByUserEntityIdAndStarEntityId(Long userId, Long starId) throws SQLException;

}
