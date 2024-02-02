package com.example.tingle.snapshot.repository;

import com.example.tingle.snapshot.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

//    List<CommentEntity> findAllById(Long commentId);
//    List<CommentEntity> findAllById(Long userId);
}
