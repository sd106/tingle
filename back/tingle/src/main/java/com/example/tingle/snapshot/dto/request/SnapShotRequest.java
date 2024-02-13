package com.example.tingle.snapshot.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnapShotRequest {

    private String content;

    private List<String> tags;

    private String username;

    private Long starId;

}
