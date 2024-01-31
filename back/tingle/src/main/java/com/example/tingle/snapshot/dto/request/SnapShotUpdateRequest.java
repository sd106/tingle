package com.example.tingle.snapshot.dto.request;

import com.example.tingle.snapshot.entity.SnapShotTag;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnapShotUpdateRequest {

    private String content;

    private List<String> tags;
}
