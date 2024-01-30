package com.example.tingle.follow.dto.response;

import com.example.tingle.follow.dto.request.FollowReadRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowReadResponse {

    String code;

    String message;

    ArrayList<FollowReadRequest> data;

}
