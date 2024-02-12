package com.example.tingle.snapshot.entity;

import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.repository.HashTagRepository;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnapShotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snapshot_id")
    private Long id;

    private String imageUrl;

    private String content;

    /**
     * 중간 테이블과의 연결 속성
     */
    @Builder.Default
    @OneToMany(mappedBy = "snapShotEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SnapShotTag> snapShotTags = new ArrayList<>();

    /**
     * 스냅샷 생성 시간과 수정 시간
     */
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    /**
     * 좋아요
     */
    @OneToMany(mappedBy = "snapShot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> likes = new ArrayList<>();

    // 스냅샷에 좋아요를 추가하는 메소드
    public void addLike(LikeEntity like) {
        this.likes.add(like);
        like.setSnapShot(this);
    }

    // 스냅샷의 좋아요 수를 반환하는 메소드
    public int getLikesCount() {
        return likes.size();
    }


    /**
     * 스타와 팬
     */
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(targetEntity = StarEntity.class, fetch = FetchType.LAZY)
    private StarEntity star;

    /**
     * 코멘트
     */
    @OneToMany(mappedBy = "snapShotEntity", cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments = new ArrayList<>();


    public void update(String image, SnapShotUpdateRequest dto, HashTagRepository hashTagRepository) {

        this.imageUrl = image;
        this.content = dto.getContent();

        // 기존 태그 리스트를 클리어하고 새 태그를 추가
        this.snapShotTags.clear();
        for (String tagName : dto.getTags()) {
            HashTagEntity hashTagEntity = hashTagRepository.findByTag(tagName)
                    .orElseGet(() -> {
                        HashTagEntity newTag = HashTagEntity.builder()
                                .tag(tagName)
                                .build();

                        return hashTagRepository.save(newTag); // 새로운 태그 저장
                    });
        }
    }

    public void changeSnapShotTags(List<SnapShotTag> snapShotTags) {
        this.snapShotTags.clear();
        this.snapShotTags.addAll(snapShotTags);
    }
}
