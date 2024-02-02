package com.example.tingle.snapshot.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String context;
    private String username;
    private Long snapshotId;
}
