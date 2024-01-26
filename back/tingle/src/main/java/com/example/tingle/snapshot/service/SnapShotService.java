package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnapShotService {

    private SnapShotRepository snapShotRepository;
    // 추천수별 정렬된 게시글 목록 조회
    public List<SnapShotEntity> getSnapShotsByLikes(SnapShotEntity snapShot) {
        return snapShotRepository.findAllByLikesOrderByLikesDesc(snapShot);
    }

    // 최신순 정렬된 게시글 목록 조회
    public List<SnapShotEntity> getSnapShotsByCreatedTime(SnapShotEntity snapShot) {
        return snapShotRepository.findAllByCreatedTimeOrderByCreatedTimeDesc(snapShot);
    }
}
