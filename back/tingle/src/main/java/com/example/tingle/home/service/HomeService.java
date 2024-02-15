package com.example.tingle.home.service;

import com.example.tingle.home.dto.HomeDto;
import com.example.tingle.home.dto.HomeProfileDto;
import com.example.tingle.home.dto.request.HomeCreateRequest;
import com.example.tingle.home.dto.request.HomeProfileCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HomeService {

    //글 조회
    List<HomeDto> findHomesByStarId(Long StarId);

    boolean insertHomePencil(HomeCreateRequest homeCreateRequest);

    boolean insertHomePictures(String homeRequestJson, List<MultipartFile> files) throws IOException;
//
//    boolean updateHome(String homeRequestJson, List<MultipartFile> files, Long ordering) throws IOException;

    boolean deleteHome(Long homeId);

    HomeProfileDto findHomeProfile(Long starId);

    boolean insertHomeProfile(Long starId, HomeProfileCreateRequest homeProfileCreateRequest);
}
