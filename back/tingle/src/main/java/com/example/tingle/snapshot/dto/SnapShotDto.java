package com.example.tingle.snapshot.dto;

import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SnapShotDto {

    private String imageUrl;

    private String content;

    private List<String> tags;

    private UserEntity user;

    private StarEntity star;
}
