package com.example.tingle.store.controller;


import com.example.tingle.common.ResultDTO;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.service.impl.StarServiceImpl;
import com.example.tingle.store.dto.OrderDto;
import com.example.tingle.store.entity.OrderEntity;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.service.S3UploadService;
import com.example.tingle.store.service.impl.OrderServiceImpl;
import com.example.tingle.store.service.impl.ProductServiceImpl;
import com.example.tingle.user.dto.UserStoreStorageDTO;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.entity.UserStoreStorage;
import com.example.tingle.user.service.impl.UserServiceImpl;
import com.example.tingle.user.service.impl.UserStoreStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class StarOrderController {
    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderService;
    private final StarServiceImpl starService;
    private final UserServiceImpl userService;
    private final S3UploadService s3UploadService;
    private final UserStoreStorageServiceImpl userStoreStorageService;


    @Autowired
    public StarOrderController(OrderServiceImpl orderService,
                               StarServiceImpl starService,
                               UserServiceImpl userService,
                               ProductServiceImpl productService,
                               S3UploadService s3UploadService,
                               UserStoreStorageServiceImpl userStoreStorageService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.starService = starService;
        this.s3UploadService = s3UploadService;
        this.userStoreStorageService = userStoreStorageService;
    }


    // 주문 생성, 상품의 수량이 0일시 주문 불가능.
    // http://localhost:8080/createOrder/2/7

    @PostMapping("/create/{userId}/{productId}")
    public String createOrder(@PathVariable Integer userId, @PathVariable Long productId) {
        // 주문한 사용자와 상품을 조회
        Optional<UserEntity> optionalUserEntity = userService.findById(Long.valueOf(userId));
        Optional<ProductEntity> optionalProductEntity = productService.findById(productId);

        if (optionalProductEntity.isPresent()) {
            int amount = optionalProductEntity.get().getAmount();
            if (amount > 0) {
                if (optionalUserEntity.isPresent()) {
                    // 주문 엔터티 생성
                    if (orderService
                            .processOrder(optionalProductEntity.get().getId(),
                                    optionalUserEntity.get().getId(),
                                    optionalProductEntity.get().getStarId().getUsername())) {
                        return "SUCCESS";
                    }
                }
            }
        }
        return "FAIL";
    }


    //http://localhost:8080/deleteOrder/6
    @PostMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        Optional<OrderEntity> order = orderService.findById(orderId);
        if (order.isPresent()) {
            Long starId = order.get().getGoods().getStarId().getId();

            orderService.deleteById(orderId);
            orderService.deleteOrderFromStar(starId, orderId);
            return "SUCCESS";
        }
        return "FAIL";
    }

    //http://localhost:8080/getOrderByStarName/test02
    @GetMapping("/getByStarName/{starName}")
    public ResultDTO<List<OrderDto>> getOrderByStarName(@PathVariable String starName) {
        StarEntity star = starService.findByUsername(starName);

        if (star != null) {
            List<OrderEntity> orderEntities = star.getOrderEntities();
            // OrderDto로 변환
            List<OrderDto> orderDtos = orderEntities.stream()
                    .map(this::convertToOrderDto)
                    .collect(Collectors.toList());
            // ResultDTO로 결과를 래핑하여 반환
            return ResultDTO.of("SUCCESS", "주문 목록 조회 성공", orderDtos);
        } else {
            // StarEntity를 찾지 못한 경우 빈 리스트를 ResultDTO로 반환
            return ResultDTO.of("NOT_FOUND", "스타를 찾을 수 없음", new ArrayList<>());
        }
    }

    public OrderDto convertToOrderDto(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();

        orderDto.setOrderId(orderEntity.getOrderId());
        orderDto.setFan(userService.mapToDTO(orderEntity.getFan()));
        orderDto.setGoods(productService.mapToDTO(orderEntity.getGoods()));

        return orderDto;
    }


    @GetMapping("/getById/{orderId}")
    public ResultDTO<OrderDto> getOrderInfo(@PathVariable Long orderId) {
        Optional<OrderEntity> orderEntityOptional = orderService.findById(orderId);

        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = orderEntityOptional.get();
            OrderDto orderDto = convertToOrderDto(orderEntity);

            // 성공적인 결과를 ResultDTO로 래핑하여 반환
            return ResultDTO.of("SUCCESS", "주문 정보 조회 성공", orderDto);
        } else {
            // 주문을 찾지 못한 경우 실패 결과를 ResultDTO로 반환
            return ResultDTO.of("NOT_FOUND", "주문을 찾을 수 없음", null);
        }
    }


    //유에에게 사진 보내기
    @PostMapping("/sendTouser")
    public String sendTOuser(@RequestParam("files") MultipartFile[] files,
                             @RequestParam("orderId") Long orderId,
                             @RequestParam("content") String content,
                             @RequestParam("title") String title) throws IOException {
        Optional<OrderEntity> optionalOrderEntity = orderService.findById(orderId);
        if (optionalOrderEntity.isPresent()) {
            String starName = optionalOrderEntity.get().getGoods().getStarName();
            Long userId = optionalOrderEntity.get().getFan().getId();
            Optional<UserEntity> optionalUserEntity = userService.findById(userId);

            StarEntity starEntity = starService.findByUsername(starName);
            UserEntity userEntity = optionalUserEntity.get();
            List<UserStoreStorage> storeStorages = userEntity.getStoreStorages();

            for (MultipartFile file : files) {
                String imageUrl = s3UploadService.saveFile(file); // S3에 파일 업로드
                UserStoreStorage userStoreStorage = UserStoreStorage.builder()
                        .url(imageUrl)
                        .Content(content)
                        .title(title)
                        .star(starEntity)
                        .user(userEntity)
                        .build();
                storeStorages.add(userStoreStorage);
                userStoreStorageService.save(userStoreStorage);
                userService.save(userEntity);
                starService.save(starEntity);
            }
            List<String> collect = storeStorages.stream().map(UserStoreStorage::getUrl)
                    .toList();
            return collect.toString();
        }
        return "FAIL";
    }


    @PostMapping("/sendTouser/delete/{storageId}")
    public String sendToUserDelete(@PathVariable("storageId") Long storageId) {
        Optional<UserStoreStorage> optionalUserStoreStorage = userStoreStorageService.findById(storageId);
        UserStoreStorage userStoreStorage = optionalUserStoreStorage.get();
        UserEntity user = userStoreStorage.getUser();

        List<UserStoreStorage> storeStorages = user.getStoreStorages();
        for (UserStoreStorage storeStorage : storeStorages) {
            if (storeStorage.getStorageId().equals(storageId)) {
                storeStorages.remove(storeStorage);
                userStoreStorageService.deleteById(storageId);
                s3UploadService.deleteImage(storeStorage.getUrl());
                return "SUCSESS";
            }
        }
        return "FAIL";
    }

    @GetMapping("/getStarsPicture/{userId}")
    public ResultDTO<List<UserStoreStorageDTO>> getStarsPicture(@PathVariable Long userId) {
        Optional<UserEntity> optionalUserEntity = userService.findById(userId);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            List<UserStoreStorage> storeStorages = userEntity.getStoreStorages();
            List<UserStoreStorageDTO> userStoreStorageDTOS = storeStorages.stream()
                    .map(UserStoreStorageDTO::convertToDto)
                    .toList();
            return ResultDTO.of("SUCCESS", "유저가 구매한 사진 불러오기 성공", userStoreStorageDTOS);
        }
        return ResultDTO.of("FAIL", "유저가 구매한 사진 불러오기 실패", new ArrayList<>());
    }

}



