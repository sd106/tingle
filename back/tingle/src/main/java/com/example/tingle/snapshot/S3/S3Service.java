package com.example.tingle.snapshot.S3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile, String modifiedFilename) throws IOException {
        String originalFilename = modifiedFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public void deleteImage(String originalFilename)  {
        try {

            String Filename = extractFileName(originalFilename);
            String EncodeUrl = URLDecoder.decode(Filename, StandardCharsets.UTF_8);
            amazonS3.deleteObject(bucket, EncodeUrl);

            System.out.println("originalFilename = " + originalFilename);
            System.out.println("Filename = " + EncodeUrl);
        } catch (AmazonServiceException e) {
            log.error("Error occurred while deleting file from S3", e);
            throw e; // Or handle it based on your application logic
        }
    }

    public String extractFileName(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
    }
}