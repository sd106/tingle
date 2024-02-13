package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.dto.request.CommentRequest;
import com.example.tingle.snapshot.entity.CommentEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.repository.CommentRepository;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final SnapShotRepository snapShotRepository;
    private final CommentRepository commentRepository;


    @Override
    public List<CommentEntity> getComment() {
        return commentRepository.findAll();
    }

    @Override
    public void createComment(CommentRequest commentRequest) {
        System.out.println("댓글 만들러 서비스 들어옴");
        System.out.println("commentRequest.getIsStar() = " + commentRequest.getIsStar());
        Optional<SnapShotEntity> optSnapShot = snapShotRepository.findById(commentRequest.getSnapshotId());

        CommentEntity commentEntity = CommentEntity.builder()
                .context(commentRequest.getContext())
                .username(commentRequest.getUsername())
                .snapShotEntity(optSnapShot.get())
                .isStar(commentRequest.getIsStar())
                .build();

        commentRepository.save(commentEntity);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public Long updateComment(Long commentId, CommentRequest commentRequest) {
        Optional<CommentEntity> optComment = commentRepository.findById(commentId);

        CommentEntity commentEntity = optComment.get();

        commentEntity.update(commentRequest.getContext());

        commentRepository.save(commentEntity);

        return commentEntity.getId();
    }
}
