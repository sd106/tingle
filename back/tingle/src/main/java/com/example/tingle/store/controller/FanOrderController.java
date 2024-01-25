package com.example.tingle.store.controller;

import com.example.tingle.store.dto.OrderDto;
import com.example.tingle.store.entity.OrderEntity;
import com.example.tingle.store.service.ProductService;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FanOrderController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductService productService;

    public FanOrderController(UserRepository userRepository,
                              UserService userService,
                              ProductService productService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/getUserOrders/{username}")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            List<OrderDto> orderDtos = user.getOrders().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orderDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private OrderDto convertToDto(OrderEntity order) {
        OrderDto dto = new OrderDto();

        dto.setFanId(order.getFan().getId());
        dto.setFan(userService.mapToDTO(order.getFan()));
        dto.setGoods(productService.mapToDTO(order.getGoods())); // 여기서 'getName()'은 ProductEntity의 상품명을 가져오는 가정
        return dto;
    }
}
