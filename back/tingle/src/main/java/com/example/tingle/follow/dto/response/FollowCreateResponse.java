package com.example.tingle.follow.dto.response;

import com.example.tingle.follow.dto.FollowDto;
import com.example.tingle.follow.dto.request.FollowReadRequest;
import com.example.tingle.follow.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowCreateResponse {

    String code;

    String message;

    FollowDto data;

}