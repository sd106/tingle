
package com.example.tingle.user.service;

import com.example.tingle.user.dto.UserDto;
import com.example.tingle.user.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // 다른 필드 및 메서드...

    public UserDto mapToDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .picture(userEntity.getPicture())
                .role(userEntity.getRole())
                .provider(userEntity.getProvider())
                .email(userEntity.getEmail())
                .build();

        return userDto;
    }
}
