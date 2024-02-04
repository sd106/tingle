package com.example.tingle.home.service;

import com.example.tingle.home.dto.HomeDto;
import com.example.tingle.home.dto.request.HomeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HomeService {

    //글 조회
    List<HomeDto> findHomesByStarId(Long StarId);

    boolean insertHome(HomeRequest homeRequest, List<MultipartFile> files) throws IOException;

}
