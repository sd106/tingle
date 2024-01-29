package com.example.tingle.snapshot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SnapShotTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "snapshot_id")
    private SnapShotEntity snapShotEntity;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashTagEntity hashTagEntity;
}
