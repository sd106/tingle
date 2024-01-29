package com.example.tingle.snapshot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "tag")
    private String tag;

    @OneToMany(mappedBy = "hashTagEntity", cascade = CascadeType.ALL)
    private List<SnapShotTag> snapShotTags = new ArrayList<>();
}
