package com.example.tingle.home.service;

import com.example.tingle.home.dto.HomeDto;
import com.example.tingle.home.dto.request.HomeRequest;
import com.example.tingle.home.entity.HomeEntity;
import com.example.tingle.home.entity.HomePictureEntity;
import com.example.tingle.home.repository.HomePictureRepository;
import com.example.tingle.home.repository.HomeRepository;
import com.example.tingle.snapshot.S3.S3Service;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Service
@RequiredArgsConstructor
@Slf4j
public class HomeServiceImpl implements HomeService {

    private final StarRepository starRepository;
    private final HomeRepository homeRepository;
    private final HomePictureRepository homePictureRepository;
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
    public boolean insertHome(@RequestBody HomeRequest homeRequest, @RequestParam List<MultipartFile> files) throws IOException {

        StarEntity starEntity = starRepository.findById(homeRequest.getStarId())
                .orElseThrow(() -> new IllegalArgumentException("StarEntity not found"));

        HomeEntity homeEntity = homeRepository.save(HomeEntity.builder()
                .ordering(homeRequest.getOrdering())
                .content(homeRequest.getContent())
                .starEntity(starEntity)
                .build());

        List<HomePictureEntity> homePictureEntities = new ArrayList<>();
        for (MultipartFile file : files) {
            String modifiedFilename = appendCurrentTimestampToFilename(file.getOriginalFilename());
            String imagePath = s3Service.saveFile(file,modifiedFilename);
            HomePictureEntity homePictureEntity = HomePictureEntity.builder()
                    .image(imagePath)
                    .homeEntity(homeEntity)
                    .build();

            homePictureEntities.add(homePictureEntity);
        }

        homePictureRepository.saveAll(homePictureEntities);

        return true;
    }

    public String appendCurrentTimestampToFilename(String originalFilename) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex > 0) {
            String nameWithoutExtension = originalFilename.substring(0, dotIndex);
            String extension = originalFilename.substring(dotIndex);
            return nameWithoutExtension + "_" + timestamp + extension;
        } else {
            return originalFilename + "_" + timestamp;
        }
    }
}






