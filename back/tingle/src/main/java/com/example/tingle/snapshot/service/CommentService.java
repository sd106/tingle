package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.dto.request.CommentRequest;
import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    List<CommentEntity> getComment();

    void createComment(CommentRequest commentRequest);

    void deleteComment(Long commentId);

    Long updateComment(Long commentId, CommentRequest commentRequest);
}
