package com.example.tingle.store.service.impl;

import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.entity.ProductImageEntity;
import com.example.tingle.store.repository.ProductRepository;
import com.example.tingle.store.service.ProductService;
import com.example.tingle.store.service.S3UploadService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final S3UploadService s3UploadService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              S3UploadService s3UploadService) {
        this.productRepository = productRepository;
        this.s3UploadService = s3UploadService;
    }


    @Override
    public ProductDto mapToDTO(ProductEntity productEntity) {
        List<String> imageUrls = productEntity.getImageUrl().stream()
                .map(ProductImageEntity::getUrl) // URL 추출
                .collect(Collectors.toList());
        ProductDto productDTO = new ProductDto();
        productDTO.setName(productEntity.getName());
        productDTO.setAmount(productEntity.getAmount());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setImageUrl(imageUrls);
        productDTO.setContent(productEntity.getContent());
        productDTO.setAvailable(productEntity.isAvailable());
        return productDTO;
    }

    @Override
    public ProductEntity convertDtoToEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
//        productEntity.setStarId(productDto.getStarId()); // 예를 들어, 이런 식으로 필드를 설정
        productEntity.setStarName(productDto.getStarName());
        productEntity.setName(productDto.getName());
        productEntity.setAmount(productDto.getAmount());
        productEntity.setPrice(productDto.getPrice());
        List<ProductImageEntity> imageEntities = productDto.getImageUrl().stream()
                .map(url -> new ProductImageEntity(url, productEntity)) // 여기서 ProductImageEntity 생성
                .collect(Collectors.toList());
        productEntity.setImageUrl(imageEntities);
        productEntity.setContent(productDto.getContent());
        productEntity.setAvailable(productDto.isAvailable());

        return productEntity;
    }


    @Override
    public Optional<ProductEntity> findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void save(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    @Override
    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductEntity> findByStarName(String starName) {
        return productRepository.findByStarName(starName);
    }

    public ProductDto createOrUpdateProduct(ProductDto productDto, MultipartFile[] imageFiles) throws IOException {
        List<String> imageUrls = s3UploadService.uploadMultipleFiles(imageFiles);
//        productDto.setImageUrl(imageUrls);

        ProductEntity productEntity = convertDtoToEntity(productDto);
        productEntity = productRepository.save(productEntity);

        return mapToDTO(productEntity);
    }

    @Transactional
    public void updateProductImage(Long productId, String existingImageUrl, String newImageUrl) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found"));

        // 특정 이미지만 제거
        if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
            product.getImageUrl().removeIf(image -> image.getUrl().equals(existingImageUrl));
        }

        // 새 이미지 추가
        if (newImageUrl != null && !newImageUrl.isEmpty()) {
            product.getImageUrl().add(new ProductImageEntity(newImageUrl, product));
        }

        productRepository.save(product); // 변경 사항 저장
    }
}
