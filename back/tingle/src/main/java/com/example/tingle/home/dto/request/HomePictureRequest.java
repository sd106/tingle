package com.example.tingle.home.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HomePictureRequest {

    private Long id;

    private int ordering;

}
