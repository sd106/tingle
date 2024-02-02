package com.example.tingle.homeArticle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "homeArticlePicture")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeArticlePictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String picture;

    @ManyToOne
    private HomeArticleEntity homeArticleEntity;


}
