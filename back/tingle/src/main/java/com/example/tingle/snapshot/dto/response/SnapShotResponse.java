package com.example.tingle.snapshot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SnapShotResponse {

    private String imageUrl;

    private String content;

    private List<String> tags;

    private Long userId;

    private Long starId;
}
