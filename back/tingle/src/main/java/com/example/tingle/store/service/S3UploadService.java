package com.example.tingle.store.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.tingle.store.dto.ProductImageDto;
import com.example.tingle.store.entity.ProductImageEntity;
import com.example.tingle.store.repository.ProductImageRepository;
import com.example.tingle.store.repository.ProductRepository;
import com.example.tingle.user.entity.UserStoreStorage;
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
public interface S3UploadService {

    void deleteById(Long imageId);

    void save(ProductImageEntity productImageEntity);
    void save(UserStoreStorage userStoreStorage);

    ProductImageEntity findById(Long imageId);

    String saveFile(MultipartFile multipartFile) throws IOException;

    void deleteImage(String originalFilename);

    List<String> uploadMultipleFiles(MultipartFile[] files) throws IOException;

}