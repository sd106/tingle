package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.HashTagEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.entity.SnapShotTag;
import com.example.tingle.snapshot.repository.HashTagRepository;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SnapShotServiceImpl implements SnapShotService {

    private final SnapShotRepository snapshotRepository;
    private final HashTagRepository hashTagRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;

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

        // 해시태그 처리
        for (String tag : tags) {
            boolean existedTag = hashTagRepository.existsByTag(tag);

            HashTagEntity hashTagEntity;

            // 이미 태그가 db에 존재한다면
            if (existedTag) {
                hashTagEntity = hashTagRepository.findByTag(tag);

            } else {
                hashTagEntity = HashTagEntity.builder()
                        .tag(tag)
                        .build();
            }

            SnapShotTag snapShotTag = SnapShotTag.builder()
                    .snapShotEntity(snapshotEntity)
                    .hashTagEntity(hashTagEntity)
                    .build();


            snapshotEntity.getSnapShotTags().add(snapShotTag);
            hashTagEntity.getSnapShotTags().add(snapShotTag);

            hashTagRepository.save(hashTagEntity);
        }
        // SnapshotRepository에 다시 저장하여 연관 관계 업데이트
        snapshotRepository.save(snapshotEntity);
    }

    @Override
    public void deleteSnapShot(Long snapshotId) {
        snapshotRepository.deleteById(snapshotId);
    }

    @Override
    public Long updateSnapShot(Long snapshotId, SnapShotUpdateRequest snapShotUpdateRequest) {
        Optional<SnapShotEntity> optSnapShot = snapshotRepository.findById(snapshotId);

        if (optSnapShot.isEmpty()) {
            System.out.println("스냅샷이 없습니다만? 오류 났어요~~");
            return null;
        }

        SnapShotEntity snapShotEntity = optSnapShot.get();

        snapShotEntity.update(snapShotUpdateRequest);

        return snapShotEntity.getId();
    }

    // 추천수별 정렬된 게시글 목록 조회
    @Override
    public List<SnapShotEntity> getSnapShotsByLikes(SnapShotEntity snapShot) {
        return snapshotRepository.findAllByLikesOrderByLikesDesc(snapShot);
    }

    // 최신순 정렬된 게시글 목록 조회
    @Override
    public List<SnapShotEntity> getSnapShotsByCreatedTime(SnapShotEntity snapShot) {
        return snapshotRepository.findAllByCreatedTimeOrderByCreatedTimeDesc(snapShot);
    }
}
