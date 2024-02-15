package com.example.tingle.wish.entity;

import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Wish")
public class WishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    private Long id; // 위시 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user; // 유저 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id")
    private StarEntity star; // 스타 아이디

    @Column(name = "status")
    private int status; // 채택 여부

    @Column(name = "points")
    private int points; // 미션금

    @Column(name = "liked_count")
    private int likedCount; // 추천 수

    @Column(name = "contents")
    private String contents; // 내용

    @Column(name = "create_time")
    private LocalDateTime createTime; // 등록 날짜

    @Column(name = "delete_time")
    private LocalDateTime deleteTime; // 마감 날짜

    @PrePersist
    public void setTime() {
        this.createTime = LocalDateTime.now();
        this.deleteTime = LocalDateTime.now();
    }
}
