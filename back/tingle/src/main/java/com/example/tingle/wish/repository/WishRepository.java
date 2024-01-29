package com.example.tingle.wish.repository;

import com.example.tingle.wish.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {

}