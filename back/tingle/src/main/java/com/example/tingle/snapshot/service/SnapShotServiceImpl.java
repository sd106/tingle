package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.HashTagEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.entity.SnapShotTag;
import com.example.tingle.snapshot.repository.HashTagRepository;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import com.example.tingle.snapshot.repository.SnapShotTagRepository;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SnapShotServiceImpl implements SnapShotService {

    private final SnapShotRepository snapshotRepository;
    private final HashTagRepository hashTagRepository;
    private final SnapShotTagRepository snapShotTagRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;

    public List<SnapShotEntity> getAllSnapShot() {
        return snapshotRepository.findAll();
    }

    /**
     * @param snapshotRequest : imageUrl, content, tags
     */
    @Override
    public void uploadSnapshot(SnapShotRequest snapshotRequest) {
        // SnapshotCreateDto에서 필요한 정보 추출
        String imageUrl = snapshotRequest.getImageUrl();
        String content = snapshotRequest.getContent();
        List<String> tags = snapshotRequest.getTags();
        String username = snapshotRequest.getUsername();
        String starname = snapshotRequest.getStarname();

        // 사용자 정보 가져오기 (예: username은 고정된 값)
        UserEntity user = userRepository.findByUsername(username);

        // 스타 정보 가져오기 (예: starName은 고정된 값)
        StarEntity star = starRepository.findByUsername(starname);

        SnapShotEntity snapshotEntity = SnapShotEntity.builder()
                .imageUrl(imageUrl)
                .content(content)
                .user(user)
                .star(star)
                .build();

        snapshotRepository.save(snapshotEntity);

        // 해시태그 처리
        for (String tag : tags) {
            HashTagEntity hashTagEntity;

            if (hashTagRepository.existsByTag(tag)) {
                hashTagEntity = hashTagRepository.findByTag(tag);
            } else {
                hashTagEntity = HashTagEntity.builder().tag(tag).build();
                hashTagRepository.save(hashTagEntity); // 새로운 HashTagEntity 저장
            }

            SnapShotTag snapShotTag = SnapShotTag.builder()
                    .snapShotEntity(snapshotEntity)
                    .hashTagEntity(hashTagEntity)
                    .build();

            snapShotTagRepository.save(snapShotTag); // SnapShotTag 저장
        }
    }

    @Override
    public void deleteSnapShot(Long snapshotId) {
        snapshotRepository.deleteById(snapshotId);
    }

    @Override
    public Long updateSnapShot(Long snapshotId, SnapShotUpdateRequest snapShotUpdateRequest) {
        SnapShotEntity snapShotEntity = snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new EntityNotFoundException("스냅샷이 존재하지 않습니다: " + snapshotId));

        snapShotEntity.update(snapShotUpdateRequest);
        return snapShotEntity.getId();
    }

    // 추천수별 정렬된 게시글 목록 조회
    @Override
    public List<SnapShotEntity> getSnapShotsByLikes() {
        return snapshotRepository.findAllByOrderByLikesDesc();
    }

    // 최신순 정렬된 게시글 목록 조회
    @Override
    public List<SnapShotEntity> getSnapShotsByCreatedTime() {
        return snapshotRepository.findAllByOrderByCreatedTimeDesc();
    }
}
