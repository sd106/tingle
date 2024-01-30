package com.example.tingle.snapshot.dto;

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
