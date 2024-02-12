package com.example.tingle.store.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.tingle.store.entity.ProductImageEntity;
import com.example.tingle.store.repository.ProductImageRepository;
import com.example.tingle.store.service.S3UploadService;
import com.example.tingle.user.entity.UserStoreStorage;
import com.example.tingle.user.repository.UserStoreStorageRepository;
import com.example.tingle.user.service.impl.UserStoreStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3UploadServiceImpl implements S3UploadService {
    private final AmazonS3 amazonS3;
    private final ProductImageRepository productImageRepository;
    private final UserStoreStorageRepository userStoreStorageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void deleteById(Long imageId) {
        productImageRepository.deleteById(imageId);
    }

    public void save(ProductImageEntity productImageEntity) {
        productImageRepository.save(productImageEntity);
    }

    @Override
    public void save(UserStoreStorage userStoreStorage) {
        userStoreStorageRepository.save(userStoreStorage);
    }

    public ProductImageEntity findById(Long imageId) {
        Optional<ProductImageEntity> byId = productImageRepository.findById(imageId);
        return byId.get();
    }


    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        // 현재 시간을 타임스탬프 형식으로 변환
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 타임스탬프를 파일 이름에 추가
        String filename = insertTimestampInFilename(originalFilename, timestamp);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, filename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, filename).toString();
    }

    private String insertTimestampInFilename(String originalFilename, String timestamp) {
        if (originalFilename == null || originalFilename.isEmpty()) {
            return timestamp; // 원본 파일 이름이 없는 경우, 단순히 타임스탬프를 반환
        }

        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex > 0) {
            // 파일 확장자가 있는 경우, 파일 이름과 확장자 사이에 타임스탬프 삽입
            String nameWithoutExtension = originalFilename.substring(0, dotIndex);
            String extension = originalFilename.substring(dotIndex); // 확장자 포함 '.'
            return nameWithoutExtension + "-" + timestamp + extension;
        } else {
            // 확장자가 없는 경우, 파일 이름 끝에 타임스탬프 추가
            return originalFilename + "-" + timestamp;
        }
    }

    public void deleteImage(String originalFilename) {
        String s = extractS3KeyFromURL(originalFilename);
        amazonS3.deleteObject(bucket, s);
    }

    public String extractS3KeyFromURL(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        try {
            URL parsedUrl = new URL(url);
            String path = parsedUrl.getPath();

            // URL의 Path 부분에서 첫 번째 '/'를 제거하고 반환
            return path.startsWith("/") ? path.substring(1) : path;
        } catch (MalformedURLException e) {
            // URL 형식이 잘못된 경우
            e.printStackTrace();
            return null;
        }
    }


    public List<String> uploadMultipleFiles(MultipartFile[] files) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(saveFile(file));
        }
        return urls;
    }
}
