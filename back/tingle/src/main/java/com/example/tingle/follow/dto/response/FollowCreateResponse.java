package com.example.tingle.follow.dto.response;

import com.example.tingle.follow.dto.FollowDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowCreateResponse {

    String code;

    String message;

    FollowDto data;

}
