package com.example.tingle.homeArticle.controller;

import com.example.tingle.follow.dto.response.Response;
import com.example.tingle.homeArticle.service.HomeArticleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "스타 홈 API")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/homeArticle")
public class HomeArticleController {
    
    private final HomeArticleServiceImpl homeArticleService;

    @Operation(summary = "스타 홈의 글 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{starId}")
    public Response getFollowing(@PathVariable Long starId) throws Exception {
        return new Response("성공", "스타 홈 글 조회", homeArticleService.getFollowList(userId));

    }
    

    
    
    
    
    
    
}
