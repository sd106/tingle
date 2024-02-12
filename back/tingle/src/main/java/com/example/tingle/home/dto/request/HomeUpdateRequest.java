package com.example.tingle.home.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeUpdateRequest {

    private Long homeId;

    private Long ordering;

    private String content;

}
