package com.example.tingle.home.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class HomePictureDto {

    private Long homeId;

    private int ordering;

    private String image;

}