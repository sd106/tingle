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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FanOrderController {
    private final UserService userService;
    private final OrderServiceImpl orderService;

    public FanOrderController(UserServiceImpl userService,
                              OrderServiceImpl orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/getUserOrders/{username}")
    public ResultDTO<List<OrderDto>> getUserOrders(@PathVariable String username) {
        UserEntity user = userService.findByUsername(username);

        if (user != null) {
            List<OrderDto> orderDtos = user.getOrders().stream()
                    .map(order -> orderService.convertToDto(order))
                    .collect(Collectors.toList());

            // 성공적인 결과를 ResultDTO로 래핑하여 반환
            return ResultDTO.of("SUCCESS", "사용자 주문 목록 조회 성공", orderDtos);
        } else {
            // 사용자를 찾지 못한 경우 실패 결과를 ResultDTO로 반환
            return ResultDTO.of("NOT_FOUND", "사용자를 찾을 수 없음", new ArrayList<>());
        }
    }

}
