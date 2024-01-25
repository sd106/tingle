package com.example.tingle.store.controller;

import com.example.tingle.store.dto.ProductDto;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.repository.OrderRepository;
import com.example.tingle.store.repository.ProductRepository;
import com.example.tingle.store.service.OrderProcess;
import com.example.tingle.store.service.ProductService;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StarProductController {

    private final ProductRepository productRepository;
    private final StarRepository starRepository;
    private final ProductService productService;

    @Autowired
    public StarProductController(ProductRepository productRepository,
                                 OrderRepository orderRepository,
                                 OrderProcess orderProcess,
                                 StarRepository starRepository,
                                 UserRepository userRepository,
                                 ProductService productService) {
        this.productRepository = productRepository;
        this.starRepository = starRepository;
        this.productService = productService;
    }


    //  http://localhost:8080/createProduct
    @PostMapping("/createProduct")
    public String createProduct(@RequestBody ProductDto productDto) { //(@RequestBody http json으로 받음

        StarEntity starEntity = starRepository.findByUsername(productDto.getStarName());
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
            productRepository.save(productEntity);
            return "create!!!!!!@@@@";
        } else {
            // starEntity가 null인 경우에 대한 예외를 던짐
            throw new NullPointerException("StarEntity를 찾을 수 없습니다.");
        }
    }


    //http://localhost:8080/deleteProduct, body에 productId=?
    @PostMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        // 해당 ID에 해당하는 Product를 삭제
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.deleteById(productId);
            return "delete!!!!";
        }
//        productRepository.deleteById(productId);

        return "없어요 ㅜㅜ 어케지워요";
    }


    //http://localhost:8080/{productId}
    @PostMapping("/updateProduct")
    public String updateProduct(@PathVariable Long productId, @RequestBody ProductDto updatedProductDto) {
        // 특정 ID에 해당하는 Product 조회
        System.out.println("productId = " + productId);
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);

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
            productRepository.save(existingProductEntity);

            return "update!!!!!!!!!!";
        } else {
            return "don't have product";
        }
    }


    // 특정 상품 조회
    // http://localhost:8080/getProductsByStar?productId=상품 Id
//    @GetMapping("/getProductById")
//    public ResponseEntity<ProductEntity> getProductById(@RequestParam Long productId) {
//        Optional<ProductEntity> productEntity = productRepository.findById(productId);
//        return productEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        if (productEntity.isPresent()) {
            ProductDto productDto = ProductDto.convertToDto(productEntity.get());
            return ResponseEntity.ok(productDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 스타의 모든 상품 조회 ->
    // http://localhost:8080/getProductsByStarName/{}  =스타이름
    @GetMapping("/getProductsByStarName/{starName}")
    public List<ProductDto> getProductsByStarName(@PathVariable String starName) {
        List<ProductEntity> products = productRepository.findByStarName(starName);

        return products.stream()
                .map(productService::mapToDTO)
                .collect(Collectors.toList());
    }

}
