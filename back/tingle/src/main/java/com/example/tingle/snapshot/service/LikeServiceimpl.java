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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeServiceimpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final SnapShotRepository snapShotRepository;

    public boolean findLike(String username, SnapShotEntity snapShot) {
        UserEntity user = userRepository.findByUsername(username);

        boolean isLike = false;

        Optional<LikeEntity> optlike = likeRepository.findByUserAndSnapShot(user, snapShot);
        if (optlike.isPresent()) {
            LikeEntity like = optlike.get();
            isLike = true;
        }
        return isLike;
    };
    @Override
    public void addLike(String username, Long snapshotId) {
        System.out.println("username = " + username);
        UserEntity user = userRepository.findByUsername(username);
        System.out.println("user = " + user.getId());

        SnapShotEntity snapShot = snapShotRepository.findById(snapshotId)
                .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));
        System.out.println("snapShot = " + snapShot);

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
    public void removeLike(String username, Long snapshotId) {

        UserEntity user = userRepository.findByUsername(username);

        SnapShotEntity snapShot = snapShotRepository.findById(snapshotId)
                .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));

        LikeEntity like = likeRepository.findByUserAndSnapShot(user, snapShot)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));
        likeRepository.delete(like);
    }
}
