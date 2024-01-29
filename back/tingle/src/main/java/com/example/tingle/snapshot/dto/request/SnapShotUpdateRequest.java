package com.example.tingle.snapshot.dto.request;

import com.example.tingle.snapshot.entity.SnapShotTag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SnapShotUpdateRequest {

    private String imageUrl;

    private String content;

    private List<SnapShotTag> tags;
}
