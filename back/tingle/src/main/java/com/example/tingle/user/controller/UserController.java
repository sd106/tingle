package com.example.tingle.user.controller;

import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info/{userId}")
    public UserEntity getUserInfo(Long userId) {
        return userService.findById(userId).orElse(null);
    }
}
