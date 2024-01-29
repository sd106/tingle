package com.example.tingle.snapshot.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SnapShotRequest {

    private String imageUrl;

    private String content;

    private List<String> tags;

    private String username;

    private String starname;

}
