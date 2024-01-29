package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.dto.request.CommentRequest;
import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;

public interface CommentService {

    void createComment(CommentRequest commentRequest);

    void deleteComment(Long commentId);

    Long updateComment(Long commentId, CommentRequest commentRequest);
}
