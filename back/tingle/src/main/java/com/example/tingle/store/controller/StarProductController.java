package com.example.tingle.store.controller;

import com.example.tingle.common.ResultDTO;
import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.service.impl.ProductServiceImpl;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.service.StarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class StarProductController {

    private final ProductServiceImpl productService;
    private final StarServiceImpl starService;

    @Autowired
    public StarProductController(ProductServiceImpl productService,
                                 StarServiceImpl starService) {
        this.productService = productService;
        this.starService = starService;
    }




    //  http://localhost:8080/createProduct
    @PostMapping("/create")
    public String createProduct(@RequestBody ProductDto productDto) { //(@RequestBody http json으로 받음

        StarEntity starEntity = starService.findByUsername(productDto.getStarName());
        if (starEntity != null) {
            System.out.println("productDto.getName() = " + productDto.getName());
            ProductEntity productEntity = ProductEntity.builder()
                    .starName(productDto.getStarName())
                    .starId(starEntity)
                    .name(productDto.getName())
                    .amount(productDto.getAmount())
                    .price(productDto.getPrice())
                    .imageUrl(productDto.getImageUrl())
                    .content(productDto.getContent())
                    .available(true)
                    .build();
            productService.save(productEntity);
            return "SUCCESS";
        } else {
            // starEntity가 null인 경우에 대한 예외를 던짐
            throw new NullPointerException("FAIL");
        }
    }


    //http://localhost:8080/deleteProduct, body에 productId=?
    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        // 해당 ID에 해당하는 Product를 삭제
        Optional<ProductEntity> product = productService.findById(productId);
        if (product.isPresent()) {
            productService.deleteById(productId);
            return "SUCCESS";
        }
        return "FAIL";
    }


    //http://localhost:8080/{productId}
    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId, @RequestBody ProductDto updatedProductDto) {
        // 특정 ID에 해당하는 Product 조회
        System.out.println("productId = " + productId);
        Optional<ProductEntity> optionalProductEntity = productService.findById(productId);

        if (optionalProductEntity.isPresent()) {
            // 기존 Product 엔터티를 가져옴
            ProductEntity existingProductEntity = optionalProductEntity.get();

            // 수정할 내용으로 기존 엔터티 업데이트
            existingProductEntity.setName(updatedProductDto.getName());
            existingProductEntity.setAmount(updatedProductDto.getAmount());
            existingProductEntity.setPrice(updatedProductDto.getPrice());
            existingProductEntity.setImageUrl(updatedProductDto.getImageUrl());
            existingProductEntity.setContent(updatedProductDto.getContent());
            existingProductEntity.setAvailable(updatedProductDto.isAvailable());

            // 수정된 내용을 저장
            productService.save(existingProductEntity);

            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }


    @GetMapping("/getById/{productId}")
    public ResultDTO<ProductDto> getProductById(@PathVariable Long productId) {
        Optional<ProductEntity> productEntity = productService.findById(productId);
        if (productEntity.isPresent()) {
            ProductDto productDto = ProductDto.convertToDto(productEntity.get());
            return ResultDTO.of("SUCCESS", "상품 상세 조회 성공",productDto);
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
