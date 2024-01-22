package com.example.tingle.follow.dto.response;

import com.example.tingle.follow.dto.request.FollowRequest;
import lombok.Data;

@Data
public class FollowResponse {

    String code;

    String message;

    FollowRequest data;

}
