package com.example.tingle.auth.dto.Request;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String name;
    private String username;
}
