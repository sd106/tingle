package com.example.tingle.auth.service;

import com.example.tingle.auth.dto.Request.SignUpRequest;
import com.example.tingle.auth.dto.Response.BasicSingupLoadResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String singup(SignUpRequest signUpRequest);

    BasicSingupLoadResponse loadBasicSingup();
}
