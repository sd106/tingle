package com.example.tingle.homeArticle.entity;

import com.example.tingle.star.entity.StarEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "homeArticle")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long order;

    @OneToMany(mappedBy = "homeArticle")
    private List<HomeArticlePictureEntity> homeArticlePictureEntity;

    @ManyToOne
    private StarEntity starEntity;


}
