package com.example.tingle.store.controller;


import com.example.tingle.store.dto.OrderDto;
import com.example.tingle.store.entity.OrderEntity;
import com.example.tingle.store.entity.ProductEntity;
import com.example.tingle.store.repository.OrderRepository;
import com.example.tingle.store.repository.ProductRepository;
import com.example.tingle.store.service.OrderProcess;
import com.example.tingle.store.service.ProductService;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import com.example.tingle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StarOrderController {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderProcess orderProcess;
    private final StarRepository starRepository;
    private final UserService userService;
    private final ProductService productService;


    @Autowired
    public StarOrderController(ProductRepository productRepository,
                               OrderRepository orderRepository,
                               OrderProcess orderProcess,
                               StarRepository starRepository,
                               UserRepository userRepository,
                               UserService userService,
                               ProductService productService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderProcess = orderProcess;
        this.starRepository = starRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.productService = productService;
    }


    // 주문 생성, 상품의 수량이 0일시 주문 불가능.
    // http://localhost:8080/createOrder?userId=??&productId=??
    @PostMapping("/createOrder/{userId}/{productId}")
    public String createOrder(@PathVariable Integer userId, @PathVariable Long productId) {
        // 주문한 사용자와 상품을 조회
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);

        if (optionalUserEntity.isPresent() && optionalProductEntity.isPresent()) {
            // 주문 엔터티 생성
            if (orderProcess.processOrder(optionalProductEntity.get().getId(),
                    optionalUserEntity.get().getId())) {
                return "Order create@!@!@!@!";
            }
        }
        return "Order Faild TTTTTT";
    }


    //http://localhost:8080/deleteOrder?orderId=??
    @PostMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        Long starId = order.get().getGoods().getStarId().getId();
//        System.out.println("starId = " + starId);

        if (order.isPresent()) {
            orderRepository.deleteById(orderId);
            orderProcess.deleteOrderFromStar(starId, orderId);
            return "delete complete!!!!!!!@@@@@";
        }

        return "없는데 어떻게 지워요;;";
    }

    //http://localhost:8080/getOrderByStarName?starName=test01
    @GetMapping("getOrderByStarName/{starName}")
    public List<OrderDto> getOrderByStarName(@PathVariable String starName) {
        StarEntity star = starRepository.findByUsername(starName);
        if (star != null) {
            List<OrderEntity> orderEntities = star.getOrderEntities();

            // OrderDto로 변환
            List<OrderDto> orderDtos = orderEntities.stream()
                    .map(this::convertToOrderDto)
                    .collect(Collectors.toList());
            System.out.println("@@@@@@@@@@@@@@2");
            return orderDtos; // OrderDto 리스트를 반환
        }

        return new ArrayList<>(); // StarEntity를 찾지 못한 경우 빈 리스트를 반환합니다.
    }

    public OrderDto convertToOrderDto(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();

        orderDto.setFan(userService.mapToDTO(orderEntity.getFan()));
        orderDto.setGoods(productService.mapToDTO(orderEntity.getGoods()));

        return orderDto;
    }


    @GetMapping("/getOrderInfo/{orderId}")
    public ResponseEntity<OrderDto> getOrderInfo(@PathVariable Long orderId) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);

        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = orderEntityOptional.get();
            OrderDto orderDto = convertToOrderDto(orderEntity);
            return ResponseEntity.ok(orderDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


