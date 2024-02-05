package com.example.tingle.wish.entity;

import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="likes")
public class LikesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id; // 추천 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user; // 유저 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WishEntity wish; // 위시 아이디

    @Column(name = "liked")
    private boolean liked; // 상태
}
