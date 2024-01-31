package com.example.tingle.wish.entity;

import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // 유저 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id", nullable = false)
    private StarEntity star; // 스타 아이디

    @Column(name = "status", nullable = false)
    private boolean status; // 채택 여부

    @Column(name = "points", nullable = false)
    private int points; // 미션금

    @Column(name = "contents", nullable = false)
    private String contents; // 내용

    @Column(name = "create_time", nullable = false)
    private LocalDate createTime; // 등록 날짜

    @Column(name = "delete_time", nullable = false)
    private LocalDate deleteTime; // 마감 날짜

    @PrePersist
    public void setTime() {
        this.createTime = LocalDate.now();
        this.deleteTime = LocalDate.now();
    }
}
