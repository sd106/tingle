package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.dto.request.CommentRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.CommentEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.repository.CommentRepository;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final SnapShotRepository snapShotRepository;
    private final CommentRepository commentRepository;

    @Override
    public void createComment(CommentRequest commentRequest) {
        Optional<SnapShotEntity> optSnapShot = snapShotRepository.findById(commentRequest.getSnapshotId());


        CommentEntity commentEntity = CommentEntity.builder()
                .context(commentRequest.getContext())
                .username(commentRequest.getUsername())
                .snapShotEntity(optSnapShot.get())
                .build();

        commentRepository.save(commentEntity);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Long updateComment(Long commentId, CommentRequest commentRequest) {
        Optional<CommentEntity> optComment = commentRepository.findById(commentId);

        if (optComment.isEmpty()) {
            System.out.println("코멘트가 없습니다만? 오류 났어요~~");
            return null;
        }

        CommentEntity commentEntity = optComment.get();

        commentEntity.update(commentRequest.getContext());

        return commentEntity.getId();
    }
}
