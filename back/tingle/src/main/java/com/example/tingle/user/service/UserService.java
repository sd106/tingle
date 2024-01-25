
package com.example.tingle.user.service;

import com.example.tingle.user.dto.UserDto;
import com.example.tingle.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    // 다른 필드 및 메서드...
    UserDto mapToDTO(UserEntity userEntity);

    Optional<UserEntity> findById(int userId);

    UserEntity findByUsername(String username);
}
