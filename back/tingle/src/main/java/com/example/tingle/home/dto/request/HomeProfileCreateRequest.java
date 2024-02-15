package com.example.tingle.home.dto.request;

import com.example.tingle.home.dto.HomeProfileDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HomeProfileCreateRequest {


    private int category= -1;

    private String snsUrl;


}
