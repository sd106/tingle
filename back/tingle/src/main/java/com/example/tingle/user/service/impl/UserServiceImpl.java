package com.example.tingle.user.service.impl;

import com.example.tingle.user.dto.UserDto;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override

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

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
