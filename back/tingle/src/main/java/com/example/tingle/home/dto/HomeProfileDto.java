package com.example.tingle.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeProfileDto {

    private String banner;

    private String profileImage;

    private String username;

    private int category;

    private String snsUrl;

}
