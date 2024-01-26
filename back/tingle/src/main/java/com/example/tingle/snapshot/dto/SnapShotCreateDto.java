package com.example.tingle.snapshot.dto;

import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SnapShotCreateDto {

    private String imageUrl;

    private String content;

    private List<String> tags;

}
