package com.example.tingle.wish.entity;

import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Long id;

    @OneToMany(mappedBy = "wish", cascade = CascadeType.REMOVE)
    private List<LikesEntity> likesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id")
    private StarEntity star;

    private boolean status;

    private int points;

    private String contents;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
}
