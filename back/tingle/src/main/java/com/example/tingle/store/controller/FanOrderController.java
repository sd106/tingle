package com.example.tingle.store.controller;

import com.example.tingle.common.ResultDTO;
import com.example.tingle.store.dto.OrderDto;
import com.example.tingle.store.service.ProductService;
import com.example.tingle.store.service.impl.OrderServiceImpl;
import com.example.tingle.store.service.impl.ProductServiceImpl;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.service.UserService;
import com.example.tingle.user.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("order")
public class FanOrderController {
    private final UserService userService;
    private final OrderServiceImpl orderService;

    public FanOrderController(UserServiceImpl userService,
                              OrderServiceImpl orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/getUserOrders/{userId}")
    public ResultDTO<List<OrderDto>> getUserOrders(@PathVariable Long userId) {
        Optional<UserEntity> byId = userService.findById(userId);

        if (byId.isPresent()) {
            UserEntity userEntity = byId.get();
            List<OrderDto> collect = userEntity.getOrders().stream()
                    .map(order -> orderService.convertToDto(order))
                    .collect(Collectors.toList());

            return ResultDTO.of("SUCCESS", "사용자 주문 목록 조회 성공", collect);
        } else {
            // 사용자를 찾지 못한 경우 실패 결과를 ResultDTO로 반환
            return ResultDTO.of("NOT_FOUND", "사용자를 찾을 수 없음", new ArrayList<>());
        }
    }
    
    @GetMapping("/processOrders")
    public int processOrders(@RequestBody List<Long> orderIds) {
        int price = 0;
        for (Long orderId : orderIds) {
            price += (int)orderService.processOrderById(orderId);
        }
        return price;
    }

}
