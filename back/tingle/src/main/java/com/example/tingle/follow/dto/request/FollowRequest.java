package com.example.tingle.follow.dto.request;

import com.example.tingle.follow.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequest {

    private Long starId;

    private String picture;

    private String userName;

    public static FollowRequest toDto(Follow follow) {
        return new FollowRequest(
                follow.getId(),
                follow.getStarEntity().getPicture(),
                follow.getStarEntity().getUsername()
        );
    }
}
