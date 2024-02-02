package com.example.tingle.star.controller;

import com.example.tingle.star.dto.response.Response;
import com.example.tingle.star.service.StarServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "스타 API")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/star")
public class StarController {

    private final StarServiceImpl starService;
    @Operation(summary = "카테고리별 상위 구독자수 스타 10명 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{category}")
    public Response getStarByCategory(@PathVariable Integer category){

        return new Response("성공", "카테고리별 스타 조회",starService.findStarsByCategory(category));

    }

    @Operation(summary = "상위 구독자수 스타 10명 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/Top10Stars")
    public Response getStarTop10(){

        return new Response("성공", "구독자 상위 10명의 스타 조회",starService.find10Stars());

    }


}
