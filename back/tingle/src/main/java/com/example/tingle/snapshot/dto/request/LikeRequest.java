package com.example.tingle.snapshot.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequest {
    private String username;
    private Boolean isStar;
}
