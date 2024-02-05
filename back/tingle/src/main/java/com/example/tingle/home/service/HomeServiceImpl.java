package com.example.tingle.home.service;

import com.example.tingle.home.dto.HomeDto;
import com.example.tingle.home.dto.request.HomeCreateRequest;
import com.example.tingle.home.dto.request.HomeUpdateRequest;
import com.example.tingle.home.entity.HomeEntity;
import com.example.tingle.home.entity.HomePictureEntity;
import com.example.tingle.home.repository.HomePictureRepository;
import com.example.tingle.home.repository.HomeRepository;
import com.example.tingle.snapshot.S3.S3Service;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.store.service.S3UploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Service
@RequiredArgsConstructor
@Slf4j
public class HomeServiceImpl implements HomeService {

    private final StarRepository starRepository;
    private final HomeRepository homeRepository;
    private final HomePictureRepository homePictureRepository;
    private final S3UploadService s3UploadService;
    private final S3Service s3Service;

    @Transactional
    @Override
    public List<HomeDto> findHomesByStarId(Long starId) {

        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new IllegalStateException("Star not found in DB"));

        List<HomeDto> homeDtos = new ArrayList<>();

        star.getHomes().forEach(home -> {
            homeDtos.add(home.toDto());
        });

        return homeDtos;

    }

    @Transactional
    @Override
    public boolean insertHome(String homeRequestJson, List<MultipartFile> files) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        HomeCreateRequest homeRequest = objectMapper.readValue(homeRequestJson, HomeCreateRequest.class);

        StarEntity starEntity = starRepository.findById(homeRequest.getStarId())
                .orElseThrow(() -> new IllegalArgumentException("StarEntity not found"));

        HomeEntity homeEntity = homeRepository.save(HomeEntity.builder()
                .ordering(homeRequest.getOrdering())
                .content(homeRequest.getContent())
                .starEntity(starEntity)
                .build());

        //s3에 이미지를 업로드한다
        List<HomePictureEntity> homePictureEntities = new ArrayList<>();

        for(MultipartFile file: files) {
            String imageUrl = s3UploadService.saveFile(file);
            HomePictureEntity homePictureEntity = HomePictureEntity.builder()
                    .image(imageUrl)
                    .homeEntity(homeEntity)
                    .build();

            homePictureEntities.add(homePictureEntity);
        }

        homePictureRepository.saveAll(homePictureEntities);

        return true;
    }

    @Transactional
    @Override
    public boolean updateHome(String homeRequestJson, List<MultipartFile> files) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        HomeUpdateRequest homeRequest = objectMapper.readValue(homeRequestJson, HomeUpdateRequest.class);

        // Home 엔티티 조회
        HomeEntity homeEntity = homeRepository.findById(homeRequest.getHomeId())
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        //기존 이미지들을 삭제한다
        List<HomePictureEntity> homePictures = homePictureRepository.findByHomeEntityId(homeRequest.getHomeId());

        // 각 HomePictureEntity에 대해 S3애서 이미지 삭제
        for (HomePictureEntity homePicture : homePictures) {
            s3Service.deleteImage(homePicture.getImage());
        }

        //s3에 이미지를 업로드한다
        List<HomePictureEntity> homePictureEntities = new ArrayList<>();

        for(MultipartFile file: files) {
            String imageUrl = s3UploadService.saveFile(file);
            HomePictureEntity homePictureEntity = HomePictureEntity.builder()
                    .image(imageUrl)
                    .homeEntity(homeEntity)
                    .build();

            homePictureEntities.add(homePictureEntity);
        }

        homePictureRepository.saveAll(homePictureEntities);

        homeEntity.setOrdering(homeRequest.getOrdering());
        homeEntity.setContent(homeRequest.getContent());
        homeEntity.setHomePictureEntities(homePictureEntities);

        homeRepository.save(homeEntity);

        return true;
    }

    @Transactional
    @Override
    public boolean deleteHome(Long homeId) {
        // HomePictureEntity 리스트 조회
        List<HomePictureEntity> homePictures = homePictureRepository.findByHomeEntityId(homeId);

        // 각 HomePictureEntity에 대해
        for (HomePictureEntity homePicture : homePictures) {
            s3UploadService.deleteImage(homePicture.getImage());
            // HomePictureEntity 삭제
            homePictureRepository.delete(homePicture);
        }

        //각 HomeEntity 삭제
        homeRepository.deleteById(homeId);

        return true;
    }

}






