package com.example.tingle.snapshot.entity;

import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnapShotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snapshot_id")
    private int id;

    private String imageUrl;

    private String content;

    @OneToMany(mappedBy = "snapshot", cascade = CascadeType.ALL)
    private Set<HashTagEntity> tags;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    private int likes;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(targetEntity = StarEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id")
    private StarEntity star;

    public void likeChange(Integer likes) {
        this.likes = likes;
    }
}
