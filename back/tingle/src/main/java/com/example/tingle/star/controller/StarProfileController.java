package com.example.tingle.star.controller;

import com.example.tingle.common.ResultDTO;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.service.impl.StarServiceImpl;
import com.example.tingle.store.service.impl.S3UploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
@RestController
public class StarProfileController {

    private final StarServiceImpl starService;
    private final S3UploadServiceImpl s3UploadService;

    @Autowired
    public StarProfileController(StarServiceImpl starService,
                                 S3UploadServiceImpl s3UploadService) {
        this.starService = starService;
        this.s3UploadService = s3UploadService;
    }


    @PostMapping("/star/profilePicture")
    public ResultDTO<String> profilePicture(@RequestParam("starId") Long fanId,
                                            @RequestParam("file") MultipartFile file) throws IOException {
        Optional<StarEntity> optionalStarEntity = starService.findById(fanId);
        if (optionalStarEntity.isPresent()) {
            StarEntity starEntity = optionalStarEntity.get();
            String picture = starEntity.getPicture();
            if (picture != null && !picture.equals("https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png")) {
                s3UploadService.deleteImage(picture);
            }
            String s = s3UploadService.saveFile(file);
            starEntity.setPicture(s);
            starService.save(starEntity);
            return ResultDTO.of("SUCCESS", "정상작동", s);
        } else {
            return ResultDTO.of("FAIL", "안됨", "안됨!!!!");
        }
    }

    @PostMapping("/star/profilePicture/delete/{starId}")
    public ResultDTO<String> profilePictureDelete(@PathVariable("starId") Long fanId) throws IOException {
        Optional<StarEntity> optionalStarEntity = starService.findById(fanId);
        if (optionalStarEntity.isPresent()) {
            StarEntity starEntity = optionalStarEntity.get();
            String picture = starEntity.getPicture();
            if (picture != null && !picture.equals("https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png")) {
                s3UploadService.deleteImage(picture);
            }
            String s = "https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png";
            starEntity.setPicture(s);
            starService.save(starEntity);
            return ResultDTO.of("SUCCESS", "정상작동", s);
        } else {
            return ResultDTO.of("FAIL", "안됨", "안됨!!!!");
        }
    }
}

