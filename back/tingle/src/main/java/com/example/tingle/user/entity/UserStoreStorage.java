package com.example.tingle.user.entity;

import com.example.tingle.star.entity.StarEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "storestorage")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStoreStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storageId;

    private String url;

    private String title;

    private String Content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "star_id")
    private StarEntity star;

    @PostPersist
    public void setOrderIdAfterPersist() {
        this.storageId = this.id;
    }
}
