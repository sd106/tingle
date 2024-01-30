package com.example.tingle.snapshot.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    private String bucketName = "your-bucket-name"; // S3 버킷 이름

    public String uploadFile(MultipartFile file) {
        String fileUrl = "";
        try {
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            fileObj.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

    public void deleteFile(String fileUrl) {
        String fileName = extractFileName(fileUrl);
        amazonS3Client.deleteObject(bucketName, fileName);
    }

    private String extractFileName(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

}
