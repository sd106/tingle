package com.example.tingle.snapshot.entity;

import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
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
    @OneToMany(mappedBy = "snapShotEntity", cascade = CascadeType.ALL)
    private List<SnapShotTag> snapShotTags = new ArrayList<>();

    /**
     * 스냅샷 생성 시간과 수정 시간
     */
    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    /**
     * 좋아요
     */
    private int likes;

    /**
     * 스타와 팬
     */
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(targetEntity = StarEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id")
    private StarEntity star;

    /**
     * 코멘트
     */
    @OneToMany(mappedBy = "snapShotEntity")
    private List<CommentEntity> comments= new ArrayList<>();


    public void likeChange(Integer likes) {
        this.likes = likes;
    }

    public void update(SnapShotUpdateRequest dto) {
        this.imageUrl = dto.getImageUrl();
        this.content = dto.getContent();
        this.snapShotTags = dto.getTags();
    }
}
