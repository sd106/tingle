package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.config.S3Service;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SnapShotServiceImpl implements SnapShotService {

    private final S3Service s3Service;
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
    public void uploadSnapshot(SnapShotRequest snapshotRequest, MultipartFile file) {
        // SnapshotCreateDto에서 필요한 정보 추출
        String imageUrl = s3Service.uploadFile(file);
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

            HashTagEntity hashTagEntity = hashTagRepository.findByTag(tag)
                    .orElseGet(() -> {
                        // 여기서 Optional이 비어 있을 때 실행될 로직을 정의합니다.
                        // 새로운 HashTagEntity를 생성하고 저장합니다.
                        HashTagEntity newTag = HashTagEntity.builder()
                                .tag(tag)
                                .build();
                        return hashTagRepository.save(newTag);
                    });

            SnapShotTag snapShotTag = SnapShotTag.builder()
                    .snapShotEntity(snapshotEntity)
                    .hashTagEntity(hashTagEntity)
                    .build();

            snapShotTagRepository.save(snapShotTag); // SnapShotTag 저장
        }
    }

    @Override
    public void deleteSnapShot(Long snapshotId) {

        SnapShotEntity snapShotEntity = snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new EntityNotFoundException("스냅샷이 존재하지 않습니다: " + snapshotId));

        // S3에서 이미지 삭제
        if (snapShotEntity.getImageUrl() != null) {
            s3Service.deleteFile(snapShotEntity.getImageUrl());
        }

        snapshotRepository.deleteById(snapshotId);
    }

    @Override
    public Long updateSnapShot(Long snapshotId, SnapShotUpdateRequest snapShotUpdateRequest) {
        SnapShotEntity snapShotEntity = snapshotRepository.findById(snapshotId)
                .orElseThrow(() -> new EntityNotFoundException("스냅샷이 존재하지 않습니다: " + snapshotId));

        // 태그 처리
        List<SnapShotTag> updatedTags = new ArrayList<>();
        for (String tagName : snapShotUpdateRequest.getTags()) {
            HashTagEntity hashTagEntity = hashTagRepository.findByTag(tagName)
                    .orElseGet(() -> {
                        HashTagEntity newTag = HashTagEntity.builder()
                                .tag(tagName)
                                .build();

                        return hashTagRepository.save(newTag); // 새로운 태그 저장
                    });
            SnapShotTag snapShotTag = SnapShotTag.builder()
                    .snapShotEntity(snapShotEntity)
                    .hashTagEntity(hashTagEntity)
                    .build();

            updatedTags.add(snapShotTag);
        }

        // 기존 태그 리스트를 업데이트된 리스트로 교체
        snapShotEntity.changeSnapShotTags(updatedTags);

        // 업데이트된 각 SnapShotTag 엔티티를 저장
        updatedTags.forEach(snapShotTagRepository::save);

        // 나머지 필드 업데이트
        snapShotEntity.update(snapShotUpdateRequest, hashTagRepository); // 이미지 URL, 콘텐츠 등 업데이트

        snapshotRepository.save(snapShotEntity); // 변경사항 저장
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
