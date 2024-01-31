package com.example.tingle.store.controller;

import com.example.tingle.store.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
public class FileUploadController {

    @Autowired
    private S3UploadService s3UploadService;

    //파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            String url = s3UploadService.saveFile(file);
            redirectAttributes.addFlashAttribute("message", "Uploaded successfully: " + url);
            return ResponseEntity.ok("File uploaded successfully: " + url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    //업로드 된 파일 삭제
    @DeleteMapping("/delete/{fileName:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
        try {
            s3UploadService.deleteImage(fileName);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File deletion failed: " + e.getMessage());
        }
    }

}
