package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SnapShotService {
    void uploadSnapshot(SnapShotRequest snapshotRequest);

    void deleteSnapShot(Long snapshotId);

    Long updateSnapShot(Long snapshotId, SnapShotUpdateRequest snapShotUpdateRequest);

    List<SnapShotEntity> getSnapShotsByLikes(SnapShotEntity snapShot);

    List<SnapShotEntity> getSnapShotsByCreatedTime(SnapShotEntity snapShot);
}
