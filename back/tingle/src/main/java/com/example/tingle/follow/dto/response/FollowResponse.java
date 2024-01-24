package com.example.tingle.follow.dto.response;

import com.example.tingle.follow.dto.request.FollowRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {

    String code;

    String message;

    ArrayList<FollowRequest> data;

}
