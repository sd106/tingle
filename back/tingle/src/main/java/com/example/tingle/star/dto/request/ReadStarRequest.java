package com.example.tingle.star.dto.request;

import com.example.tingle.follow.dto.request.FollowReadRequest;
import com.example.tingle.follow.entity.FollowEntity;
import com.example.tingle.star.entity.StarEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //푸시용
@NoArgsConstructor
@AllArgsConstructor
public class ReadStarRequest {

    private Long id;

    private String username;

    private String picture;


    public static ReadStarRequest toDto(StarEntity starEntity) {
        return new ReadStarRequest(
                starEntity.getId(),
                starEntity.getUsername(),
                starEntity.getPicture()
        );
    }

}
