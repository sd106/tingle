package com.example.tingle.store.controller;

import com.example.tingle.common.ResultDTO;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.service.StarServiceImpl;
import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.entity.ProductImageEntity;
import com.example.tingle.store.service.S3UploadService;
import com.example.tingle.store.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class StarProductController {

    private final ProductServiceImpl productService;
    private final StarServiceImpl starService;
    private final S3UploadService s3UploadService;
    private final ObjectMapper objectMapper;

    @Autowired
    public StarProductController(ProductServiceImpl productService,
                                 StarServiceImpl starService,
                                 S3UploadService s3UploadService,
                                 ObjectMapper objectMapper) {
        this.productService = productService;
        this.starService = starService;
        this.s3UploadService = s3UploadService;
        this.objectMapper = objectMapper;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestParam("productDto") String productDtoJson,
                                           @RequestParam("files") MultipartFile[] files) {
        try {
            // JSON 문자열을 ProductDto 객체로 변환
            ProductDto productDto = objectMapper.readValue(productDtoJson, ProductDto.class);

            StarEntity starEntity = starService.findByUsername(productDto.getStarName());
            if (starEntity == null) {
                throw new NullPointerException("StarEntity not found");
            }

            List<ProductImageEntity> imageEntities = new ArrayList<>();
            for (MultipartFile file : files) {
                String imageUrl = s3UploadService.saveFile(file); // S3에 파일 업로드
                imageEntities.add(new ProductImageEntity(imageUrl, null)); // S3 URL 사용
            }

            ProductEntity productEntity = ProductEntity.builder()
                    .starName(productDto.getStarName())
                    .starId(starEntity)
                    .name(productDto.getName())
                    .amount(productDto.getAmount())
                    .price(productDto.getPrice())
                    .imageUrl(imageEntities)
                    .content(productDto.getContent())
                    .available(true)
                    .build();

            imageEntities.forEach(imageEntity -> imageEntity.setProduct(productEntity));
            productService.save(productEntity);
            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product creation failed: " + e.getMessage());
        }
    }

    //http://localhost:8080/deleteProduct, body에 productId=?
    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        // 해당 ID에 해당하는 Product 조회
        Optional<ProductEntity> product = productService.findById(productId);
        if (product.isPresent()) {
            // Product와 연결된 모든 이미지 URL 조회
            List<ProductImageEntity> images = product.get().getImageUrl();

            // S3에서 각 이미지 삭제
            for (ProductImageEntity image : images) {
                s3UploadService.deleteImage(image.getUrl());
                System.out.println("image.getUrl() = " + image.getUrl());
            }

            // 데이터베이스에서 Product 삭제
            productService.deleteById(productId);
            return "SUCCESS";
        }
        return "FAIL";
    }

    //http://localhost:8080/{productId}
    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId, @RequestBody ProductDto updatedProductDto) {
        // 특정 ID에 해당하는 Product 조회
        Optional<ProductEntity> optionalProductEntity = productService.findById(productId);

        if (optionalProductEntity.isPresent()) {
            // 기존 Product 엔터티를 가져옴
            ProductEntity existingProductEntity = optionalProductEntity.get();

            // 수정할 내용으로 기존 엔터티 업데이트
            existingProductEntity.setName(updatedProductDto.getName());
            existingProductEntity.setAmount(updatedProductDto.getAmount());
            existingProductEntity.setPrice(updatedProductDto.getPrice());
            existingProductEntity.setImageUrl(existingProductEntity.getImageUrl());
            // 이미지는 각 이미지마다 업데이트 할 예정. 여기서는 업데이트 하지 않음.
            existingProductEntity.setContent(updatedProductDto.getContent());
            existingProductEntity.setAvailable(updatedProductDto.isAvailable());

            // 수정된 내용을 저장
            productService.save(existingProductEntity);

            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }

    @PostMapping(path = "/updateImg/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProductImage(@PathVariable Long productId,
                                                     @RequestParam("existingImageUrl") String existingImageUrl,
                                                     @RequestParam("file") MultipartFile file) {
        try {
            // 기존 이미지 삭제
            if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
                s3UploadService.deleteImage(existingImageUrl);
            }

            // 새 이미지 업로드
            String newImageUrl = "";
            if (file != null && !file.isEmpty()) {
                newImageUrl = s3UploadService.saveFile(file);
            }

            // 제품에 새 이미지 URL 연결
            productService.updateProductImage(productId, existingImageUrl, newImageUrl);
            return ResponseEntity.ok("Product image updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product image update failed: " + e.getMessage());
        }
    }


    @GetMapping("/getById/{productId}")
    public ResultDTO<ProductDto> getProductById(@PathVariable Long productId) {
        Optional<ProductEntity> productEntity = productService.findById(productId);
        if (productEntity.isPresent()) {
            ProductDto productDto = ProductDto.convertToDto(productEntity.get());
            return ResultDTO.of("SUCCESS", "상품 상세 조회 성공", productDto);
        } else {
            return ResultDTO.of("NOT_FOUND", "상품을 찾을 수 없음", new ProductDto());
        }
    }

    @GetMapping("/getByStarName/{starName}")
    public ResultDTO<List<ProductDto>> getProductsByStarName(@PathVariable String starName) {
        List<ProductEntity> products = productService.findByStarName(starName);

        if (!products.isEmpty()) {
            // ProductEntity 리스트를 ProductDto 리스트로 변환
            List<ProductDto> productDtos = products.stream()
                    .map(productService::mapToDTO)
                    .collect(Collectors.toList());

            // 성공적인 결과를 ResultDTO로 래핑하여 반환
            return ResultDTO.of("SUCCESS", "스타의 상품 목록 조회 성공", productDtos);
        } else {
            // 스타의 상품이 없는 경우 빈 리스트를 ResultDTO로 반환
            return ResultDTO.of("NOT_FOUND", "스타의 상품이 없음", new ArrayList<>());
        }
    }


}
