package com.example.tingle.user.dto;

import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.user.entity.Role;
import com.example.tingle.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String picture;
    private String provider;
    private String email;


    public static UserDto mapToDTO(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .picture(userEntity.getPicture())
                .provider(userEntity.getProvider())
                .email(userEntity.getEmail())
                .build();
    }
}
