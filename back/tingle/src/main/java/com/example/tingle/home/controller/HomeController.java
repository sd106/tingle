package com.example.tingle.home.controller;

import com.example.tingle.follow.dto.response.Response;
import com.example.tingle.home.dto.request.HomeCreateRequest;
import com.example.tingle.home.dto.request.HomeProfileCreateRequest;
import com.example.tingle.home.service.HomeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "스타 홈 API")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/home")
public class HomeController {

    private final HomeServiceImpl homeService;

    @Operation(summary = "스타 홈의 글 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{starId}")
    public Response getHome(@PathVariable Long starId){
        return new Response("성공", "스타 홈 글 조회", homeService.findHomesByStarId(starId));

    }

    @Operation(summary = "스타 홈의 사진타입 작성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/post/picture")
    public Response postHomePictures(@RequestParam("homeCreateRequest") String homeCreateRequestJson, @RequestParam("files") List<MultipartFile> files) throws Exception {

        return new Response("성공", "스타 홈 글 작성", homeService.insertHomePictures(homeCreateRequestJson, files));

    }

    @Operation(summary = "스타 홈의 글타입 작성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/post/pencil")
    public Response postHomePencil(@RequestBody HomeCreateRequest homeCreateRequest) throws Exception {

        return new Response("성공", "스타 홈 글 작성", homeService.insertHomePencil(homeCreateRequest));

    }

//    @Operation(summary = "스타 홈의 글 수정")
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping(value = "/update")
//    public Response updateHome(@RequestParam("homeUpdateRequest") String homeUpdateRequestJson,
//                             @RequestParam("files") List<MultipartFile> files) throws Exception {
//
//        return new Response("성공", "스타 홈 글 수정", homeService.updateHome(homeUpdateRequestJson, files));
//
//    }
//
    @Operation(summary = "스타 홈의 글 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/delete/{homeId}")
    public Response deleteHome(@PathVariable Long homeId) throws Exception {

        return new Response("성공", "스타 홈 글 삭제", homeService.deleteHome(homeId));

    }

    @Operation(summary = "스타 프로필 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profile/{starId}")
    public Response getProfile(@PathVariable Long starId){

        return new Response("성공", "스타 프로필 조회",homeService.findHomeProfile(starId));

    }

    @Operation(summary = "스타 프로필 생성")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/profile/{starId}")
    public Response updateProfile(@PathVariable Long starId, @RequestBody HomeProfileCreateRequest homeProfileCreateRequest){

        return new Response("성공", "스타 프로필 생성",homeService.insertHomeProfile(starId, homeProfileCreateRequest));

    }



}








