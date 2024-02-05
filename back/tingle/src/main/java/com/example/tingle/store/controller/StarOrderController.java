package com.example.tingle.store.controller;


import com.example.tingle.common.ResultDTO;
import com.example.tingle.store.dto.OrderDto;
import com.example.tingle.store.entity.OrderEntity;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.service.impl.OrderServiceImpl;
import com.example.tingle.store.service.impl.ProductServiceImpl;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.star.service.impl.StarServiceImpl;
import com.example.tingle.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    public StarOrderController(OrderServiceImpl orderService,
                               StarServiceImpl starService,
                               UserServiceImpl userService,
                               ProductServiceImpl productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.starService = starService;
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
        Long starId = order.get().getGoods().getStarId().getId();

        if (order.isPresent()) {
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
}


