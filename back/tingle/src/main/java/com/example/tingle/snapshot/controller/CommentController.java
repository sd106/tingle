package com.example.tingle.snapshot.controller;

import com.example.tingle.snapshot.dto.request.CommentRequest;
import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.CommentEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.service.CommentServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
@RequestMapping("/snapshot/{snapshotId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping("/comments")
    public ResponseEntity<Map<String, Object>> commentList() {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<CommentEntity> allComment = commentService.getComment();

        resultMap.put("AllComment", allComment);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/comment/new")
    public ResponseEntity<Map<String, Object>> newComment(@RequestBody CommentRequest commentRequest) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        commentService.createComment(commentRequest);

        resultMap.put("result", "성공해쓰!!");

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/comment/{commentId}/update")
    public ResponseEntity<Map<String, Object>> updateSnapShot(@PathVariable Long commentId,
                                                              @RequestBody CommentRequest commentRequest, HttpServletResponse response) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        commentService.updateComment(commentId, commentRequest);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/comment/{commentId}/delete")
    public ResponseEntity<Map<String, Object>> deleteSnapShot(@PathVariable Long commentId, HttpServletResponse response) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        commentService.deleteComment(commentId);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
