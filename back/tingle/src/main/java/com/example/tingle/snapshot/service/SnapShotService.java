package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface SnapShotService {
    void uploadSnapshot(SnapShotRequest snapshotRequest, MultipartFile file) throws IOException;

    void deleteSnapShot(Long snapshotId);

    Long updateSnapShot(Long snapshotId, SnapShotUpdateRequest snapShotUpdateRequest, MultipartFile file, String previousImageUrl) throws IOException;

    List<SnapShotEntity> getAllSnapShot(Long starId);

    List<SnapShotEntity> getAllSnapShotsByLikes(Long starId);

    List<SnapShotEntity> getAllSnapShotsByCreatedTime(Long starId);
}
