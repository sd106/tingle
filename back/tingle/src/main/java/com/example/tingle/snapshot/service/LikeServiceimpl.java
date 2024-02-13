package com.example.tingle.snapshot.service;

import com.example.tingle.snapshot.entity.LikeEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.repository.LikeRepository;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
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
    private final StarRepository starRepository;
    private final SnapShotRepository snapShotRepository;

    public boolean findLike(Boolean isStarLogin, String username, SnapShotEntity snapShot) {
        if (isStarLogin) {
            StarEntity star = starRepository.findByUsername(username);

            boolean isLike = false;

            Optional<LikeEntity> optlike = likeRepository.findByStarAndSnapShot(star, snapShot);
            if (optlike.isPresent()) {
                LikeEntity like = optlike.get();
                isLike = true;
            }
            return isLike;

        } else {
            UserEntity user = userRepository.findByUsername(username);

            boolean isLike = false;

            Optional<LikeEntity> optlike = likeRepository.findByUserAndSnapShot(user, snapShot);
            if (optlike.isPresent()) {
                LikeEntity like = optlike.get();
                isLike = true;
            }
            return isLike;
        }
    };
    @Override
    public void addLike(Boolean isStar, String username, Long snapshotId) {

        System.out.println("서비스의 addLike의 username = " + username);
        System.out.println("서비스의 addLike의 isStar = " + isStar);
        System.out.println("서비스의 addLike의 snapshotId = " + snapshotId);
        if (isStar) {
            StarEntity star = starRepository.findByUsername(username);
            System.out.println("user = " + star.getId());

            SnapShotEntity snapShot = snapShotRepository.findById(snapshotId)
                    .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));
            System.out.println("snapShot = " + snapShot);

            // 스냅샷의 스타와 현재 로그인한 유저가 스타 본인일 경우 스냅샷의 isStar를 true로 변경
            System.out.println("현재 로그인한 스타가 스냅샷의 스타인지 확인");
            System.out.println(snapShot.getStar().getUsername().equals(username));
            if (snapShot.getStar().getUsername().equals(username)) {
                System.out.println("동일인이네요");
                snapShot.starLike(isStar);
            } else {
                System.out.println("다른 사람입니다.");
            }

            likeRepository.findByStarAndSnapShot(star, snapShot)
                    .ifPresentOrElse(
                            like -> {
                                throw new IllegalStateException("Like already exists");
                            },
                            () -> {
                                LikeEntity newLike = new LikeEntity();
                                newLike.setStar(star);
                                newLike.setUser(null);
                                newLike.setSnapShot(snapShot);
                                likeRepository.save(newLike);
                            });

        } else {
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
                                newLike.setStar(null);
                                newLike.setSnapShot(snapShot);
                                likeRepository.save(newLike);
                            });
        }
    }

    @Override
    public void removeLike(Boolean isStar, String username, Long snapshotId) {
        if (isStar) {

            StarEntity star = starRepository.findByUsername(username);

            SnapShotEntity snapShot = snapShotRepository.findById(snapshotId)
                    .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));

            System.out.println("현재 로그인한 스타가 스냅샷의 스타인지 확인");
            System.out.println(snapShot.getStar().getUsername().equals(username));
            if (snapShot.getStar().getUsername().equals(username)) {
                System.out.println("동일인이네요");
                snapShot.starLike(!isStar);
            } else {
                System.out.println("다른 사람입니다.");
            }

            LikeEntity like = likeRepository.findByStarAndSnapShot(star, snapShot)
                    .orElseThrow(() -> new EntityNotFoundException("Like not found"));
            likeRepository.delete(like);

        } else {

            UserEntity user = userRepository.findByUsername(username);

            SnapShotEntity snapShot = snapShotRepository.findById(snapshotId)
                    .orElseThrow(() -> new EntityNotFoundException("Snapshot not found"));

            LikeEntity like = likeRepository.findByUserAndSnapShot(user, snapShot)
                    .orElseThrow(() -> new EntityNotFoundException("Like not found"));
            likeRepository.delete(like);

        }
    }
}
