package com.example.tingle.store.controller;

import com.example.tingle.common.ResultDTO;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.service.impl.StarServiceImpl;
import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.entity.ProductImageEntity;
import com.example.tingle.store.service.ProductImageService;
import com.example.tingle.store.service.S3UploadService;
import com.example.tingle.store.service.impl.ProductImageServiceImpl;
import com.example.tingle.store.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@Slf4j
public class StarProductController {

    private final ProductServiceImpl productService;
    private final StarServiceImpl starService;
    private final S3UploadService s3UploadService;
    private final ObjectMapper objectMapper;
    private final ProductImageServiceImpl productImageService;

    @Autowired
    public StarProductController(ProductServiceImpl productService,
                                 StarServiceImpl starService,
                                 S3UploadService s3UploadService,
                                 ObjectMapper objectMapper,
                                 ProductImageServiceImpl productImageService) {
        this.productService = productService;
        this.starService = starService;
        this.s3UploadService = s3UploadService;
        this.objectMapper = objectMapper;
        this.productImageService = productImageService;
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

    @PostMapping("/update")
    @Transactional
    public String updateProduct(@RequestParam("productDto") String productDtoJson,
                                @RequestParam("files") MultipartFile[] files,
                                @RequestParam("previewFiles") String previewFiles) throws IOException {
        System.out.println("productDtoJson = " + productDtoJson);
        ProductDto productDto = objectMapper.readValue(productDtoJson, ProductDto.class);
        Long productId = productDto.getProductId();
        Optional<ProductEntity> optionalProductEntity = productService.findById(productId);
        ProductEntity product = optionalProductEntity.get();

        List<ProductImageEntity> imageUrls = product.getImageUrl();
        List<String> previewList = Arrays.asList(previewFiles.split(","));
        List<ProductImageEntity> filteredImageUrls = new ArrayList<>();

        List<ProductImageEntity> addfilteredImageUrls = new ArrayList<>();

        for (ProductImageEntity imageUrl : imageUrls) {
            if (!previewList.contains(imageUrl.getUrl())) {
                filteredImageUrls.add(imageUrl);
            } else {
                addfilteredImageUrls.add(imageUrl);
            }
        }

        // 필터링된 리스트 사용
        for (ProductImageEntity image : filteredImageUrls) {
            System.out.println(image.getUrl());
            if (image.getId() != null) {
                productImageService.deleteById(image.getId());
                s3UploadService.deleteImage(image.getUrl());
                product.removeImage(image);
            }
        }

        for (MultipartFile file : files) {
            String url = s3UploadService.saveFile(file);
            ProductImageEntity save = productImageService.save(new ProductImageEntity(url, product));
            product.addImage(save);
        }
        for (ProductImageEntity filter : addfilteredImageUrls) {
            product.addImage(filter);
        }

        product.setName(productDto.getName());
        product.setAmount(productDto.getAmount());
        product.setPrice(productDto.getPrice());
        product.setContent(productDto.getContent());
        product.setAvailable(productDto.isAvailable());

        productService.save(product);
        return "SUCCESS";
    }

    @PostMapping("/update/nofile")
    @Transactional
    public String updateProduct(@RequestParam("productDto") String productDtoJson,
                                @RequestParam("previewFiles") String previewFiles) throws IOException {
        ProductDto productDto = objectMapper.readValue(productDtoJson, ProductDto.class);
        Long productId = productDto.getProductId();
        Optional<ProductEntity> optionalProductEntity = productService.findById(productId);
        ProductEntity product = optionalProductEntity.get();

        List<ProductImageEntity> imageUrls = product.getImageUrl();
        List<String> previewList = Arrays.asList(previewFiles.split(","));

        List<ProductImageEntity> filteredImageUrls = new ArrayList<>();

        for (ProductImageEntity imageUrl : imageUrls) {
            if (!previewList.contains(imageUrl.getUrl())) {
                filteredImageUrls.add(imageUrl);
            }
        }

        // 필터링된 리스트 사용
        for (ProductImageEntity image : filteredImageUrls) {
            if (image.getId() != null) {
                productImageService.deleteById(image.getId());
                s3UploadService.deleteImage(image.getUrl());
                product.removeImage(image);
            }
        }
        product.setName(productDto.getName());
        product.setAmount(productDto.getAmount());
        product.setPrice(productDto.getPrice());
        product.setContent(productDto.getContent());
        product.setAvailable(productDto.isAvailable());

        productService.save(product);
        return "SUCCESS";
    }


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
            }

            // 데이터베이스에서 Product 삭제
            productService.deleteById(productId);
            return "SUCCESS";
        }
        return "FAIL";
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

    @GetMapping("/getByStarId/{starId}")
    public ResultDTO<List<ProductDto>> getProductsByStarName(@PathVariable Long starId) {
        Optional<StarEntity> optionalStarEntity = starService.findById(starId);
        if (optionalStarEntity.isPresent()) {
            StarEntity starEntity = optionalStarEntity.get();
            String username = starEntity.getUsername();
            List<ProductEntity> products = productService.findByStarName(username);
            if (!products.isEmpty()) {
                // ProductEntity 리스트를 ProductDto 리스트로 변환
                List<ProductDto> productDtos = products.stream()
                        .map(productService::mapToDTO)
                        .collect(Collectors.toList());

                // 성공적인 결과를 ResultDTO로 래핑하여 반환
                return ResultDTO.of("SUCCESS", "스타의 상품 목록 조회 성공", productDtos);
            }
        }
        return ResultDTO.of("NOT_FOUND", "스타의 상품이 없음", new ArrayList<>());
    }
}