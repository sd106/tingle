package com.example.tingle.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Follow와 User간의 순환참조 문제를 해결하기 위해 Follow엔티티와 같은 구조의 Dto를 생성함
public class FollowDto {

    private Long id;
    private Long userId;
    private Long starId;

}
