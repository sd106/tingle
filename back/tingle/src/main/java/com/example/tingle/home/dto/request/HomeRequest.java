package com.example.tingle.home.dto.request;

import com.example.tingle.home.dto.HomePictureDto;
import com.example.tingle.home.entity.HomePictureEntity;
import com.example.tingle.star.entity.StarEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class HomeRequest {

    private Long starId;

    private Long ordering;

    private String content;

    private List<HomePictureDto> homePictureRequests;

}
