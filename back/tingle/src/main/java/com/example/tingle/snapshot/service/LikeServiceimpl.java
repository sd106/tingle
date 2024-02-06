package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.entity.LikeEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.repository.LikeRepository;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeServiceimpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final SnapShotRepository snapShotRepository;
    @Override
    public void addLike(Long userId, Long snapshotId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        SnapShotEntity snapShot = snapShotRepository.findById(snapshotId)
                .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));

        likeRepository.findByUserAndSnapShot(user, snapShot)
                .ifPresentOrElse(
                        like -> {
                            throw new IllegalStateException("Like already exists");
                        },
                        () -> {
                            LikeEntity newLike = new LikeEntity();
                            newLike.setUser(user);
                            newLike.setSnapShot(snapShot);
                            likeRepository.save(newLike);
                        });
    }

    @Override
    public void removeLike(Long userId, Long snapshotId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        SnapShotEntity snapShot = snapShotRepository.findById(snapshotId)
                .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));

        LikeEntity like = likeRepository.findByUserAndSnapShot(user, snapShot)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));
        likeRepository.delete(like);
    }
}
