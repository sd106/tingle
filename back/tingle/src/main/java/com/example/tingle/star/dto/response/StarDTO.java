package com.example.tingle.star.dto.response;

import com.example.tingle.star.entity.StarEntity;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarDTO {
    private Long id;
    private String name;
    private String username;
    private String picture;
    private String email;
    private int category;

    public static StarDTO mapToDto(StarEntity starEntity) {
        return StarDTO.builder()
                .id(starEntity.getId())
                .name(starEntity.getName())
                .username(starEntity.getUsername())
                .picture(starEntity.getPicture())
                .email(starEntity.getEmail())
                .category(starEntity.getCategory())
                .build();
    }


}