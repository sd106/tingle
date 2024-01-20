package com.example.tingle.controller;

import com.example.tingle.dto.joinDto;
import com.example.tingle.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    @Autowired
    private JoinService joinService;

//    @GetMapping("/signup")
//    public String joinP() {
//
//        return "join";
//    }
    
    @PostMapping("/users/new")
    public String joinProcess(@RequestBody joinDto joinDTO) {

        System.out.println("joinDTO.toString() = " + joinDTO.toString());
        System.out.println("joinDTO.getUsername() = " + joinDTO.getUsername());
        System.out.println("joinDTO.getEmail() = " + joinDTO.getEmail());

        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
