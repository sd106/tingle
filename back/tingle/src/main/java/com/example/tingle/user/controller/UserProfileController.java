package com.example.tingle.user.controller;

import com.example.tingle.common.ResultDTO;
import com.example.tingle.store.service.impl.S3UploadServiceImpl;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    private final UserServiceImpl userService;
    private final S3UploadServiceImpl s3UploadService;

    @Autowired
    public UserProfileController(UserServiceImpl userService,
                                 S3UploadServiceImpl s3UploadService) {
        this.userService = userService;
        this.s3UploadService = s3UploadService;
    }


    @PostMapping("/user/profilePicture")
    public ResultDTO<String> profilePicture(@RequestParam("fanId") Long fanId,
                                            @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("fanId = " + fanId);
        Optional<UserEntity> optionalUserEntity = userService.findById(fanId);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            String picture = userEntity.getPicture();
            if (picture != null && picture.equals("https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png")) {
                s3UploadService.deleteImage(picture);
            }
            String s = s3UploadService.saveFile(file);
            userEntity.setPicture(s);
            userService.save(userEntity);
            return ResultDTO.of("SUCCESS", "정상작동", s);
        } else {
            return ResultDTO.of("FAIL", "안됨", "안됨!!!!");
        }
    }

    @PostMapping("/user/profilePicture/delete/{fanId}")
    public ResultDTO<String> profilePictureDelete(@PathVariable("fanId") Long fanId) throws IOException {
        System.out.println("fanId = " + fanId);
        Optional<UserEntity> optionalUserEntity = userService.findById(fanId);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            String picture = userEntity.getPicture();
            if (picture != null && !picture.equals("https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png")) {
                s3UploadService.deleteImage(picture);
            }
            String s = "https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png";
            userEntity.setPicture(s);
            userService.save(userEntity);
            return ResultDTO.of("SUCCESS", "정상작동", s);
        } else {
            return ResultDTO.of("FAIL", "안됨", "안됨!!!!");
        }
    }
}
