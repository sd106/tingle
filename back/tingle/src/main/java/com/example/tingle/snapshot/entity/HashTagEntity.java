package com.example.tingle.snapshot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private int id;

    @Column(name = "tag")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "snapshot_id")
    private SnapShotEntity snapshot;
}
