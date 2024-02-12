package com.example.tingle.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeDto {

    private Long starId;

    private Long id;

    private Long ordering;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String content;

    private List<HomePictureDto> homePictureDtos;


}
