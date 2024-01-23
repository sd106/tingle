package com.example.tingle.user.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/login/oauth2/code")
public class OAuthController {

//    @GetMapping("/google")
//    public ResponseEntity<Map<String, Object>> googleLogin() {
//
//    }
}
