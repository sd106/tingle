package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.repository.LikeRepository;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import com.example.tingle.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    void addLike(Boolean isStar, String username, Long snapshotId);

    void removeLike(Boolean isStar, String username, Long snapshotId);
}
