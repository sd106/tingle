package com.example.tingle.snapshot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
@RequestMapping("/snapshot/{snapshotId}")
@RequiredArgsConstructor
public class CommentController {

    @GetMapping("/comment")
    public ResponseEntity<Map<String, Object>> commentList() {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }



}
