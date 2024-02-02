package com.example.tingle.follow.dto.request;

import com.example.tingle.follow.entity.FollowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowReadRequest {

    private Long starId;

    private String picture;

    private String userName;

    public static FollowReadRequest toDto(FollowEntity followEntity) {
        return new FollowReadRequest(
                followEntity.getStarEntity().getId(),
                followEntity.getStarEntity().getPicture(),
                followEntity.getStarEntity().getUsername()
        );
    }
}
