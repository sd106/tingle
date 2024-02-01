package com.example.tingle.store.controller;

import com.example.tingle.common.ResultDTO;
import com.example.tingle.store.dto.ProductImageDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.entity.ProductImageEntity;
import com.example.tingle.store.service.ProductService;
import com.example.tingle.store.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/s3")
public class FileUploadController {

    private final ProductService productService;
    private final S3UploadService s3UploadService;

    @Autowired
    public FileUploadController(S3UploadService s3UploadService,
                                ProductService productService) {
        this.s3UploadService = s3UploadService;
        this.productService = productService;
    }


    @PostMapping("/update/{productId}/{imageId}")
    public ResultDTO<?> updateFile(
                                        @PathVariable Long imageId,
                                        @PathVariable Long productId,
                                        @RequestParam("file") MultipartFile file) {
        try {
                Optional<ProductEntity> product = productService.findById(productId);
                if (product.isPresent()) {
                    List<ProductImageEntity> imageUrl = product.get().getImageUrl();
                    for (ProductImageEntity image : imageUrl) {
                        if (Objects.equals(image.getId(), imageId)) {
                            s3UploadService.deleteImage(image.getUrl());
                            String newFileUrl = s3UploadService.saveFile(file);
                            image.setUrl(newFileUrl);
                            s3UploadService.save(image);
                            ProductImageDto productImageDto = new ProductImageDto(newFileUrl, productId, imageId);
                            return ResultDTO.of("SUCCESS", "상품 업데이트 성공", productImageDto);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResultDTO.of("FAIL", "상품 업데이트 실패", new ArrayList<>());
            }
        return ResultDTO.of("FAIL", "상품 업데이트 실패", new ArrayList<>());
    }

    @PostMapping("/delete/{imageId}")
    public void deleteFile(@PathVariable Long imageId) {
        ProductImageEntity image = s3UploadService.findById(imageId);
        s3UploadService.deleteImage(image.getUrl());
        s3UploadService.deleteById(imageId);
    }


        //파일 업로드
    @PostMapping("/upload/{productId}")
    public ResponseEntity<?> uploadFile(@PathVariable Long productId ,@RequestParam("file") MultipartFile file) {
        try {
            Optional<ProductEntity> product = productService.findById(productId);
            if (product.isPresent()) {
                String newUrl = s3UploadService.saveFile(file);

                ProductEntity productEntity = product.get();
                List<ProductImageEntity> imageUrl = productEntity.getImageUrl();
                ProductImageEntity image = new ProductImageEntity(newUrl, productEntity);
                imageUrl.add(image);

                s3UploadService.save(image);
                productService.save(productEntity);

                return ResponseEntity.ok("File uploaded successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
        return null;
    }


}
