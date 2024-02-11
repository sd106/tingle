package com.example.tingle.user.dto;


import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.entity.UserStoreStorage;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStoreStorageDTO {

    private Long id;
    private Long storageId;
    private String url;
    private String title;
    private String content;
    private Long userId;
    private Long starId;
    private String username;
    private String starname;

    public static UserStoreStorageDTO convertToDto(UserStoreStorage entity) {
        UserStoreStorageDTO dto = new UserStoreStorageDTO();
        dto.setId(entity.getId());
        dto.setStorageId(entity.getStorageId());
        dto.setUrl(entity.getUrl());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
            dto.setUsername(entity.getUser().getUsername());
        }
        if (entity.getStar() != null) {
            dto.setStarId(entity.getStar().getId());
            dto.setStarname(entity.getStar().getUsername());
        }
        return dto;
    }
}
