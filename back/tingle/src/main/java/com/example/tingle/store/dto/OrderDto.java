package com.example.tingle.store.dto;
import com.example.tingle.store.entity.OrderEntity;
import com.example.tingle.store.service.ProductService;
import com.example.tingle.user.service.UserService;
import com.example.tingle.user.dto.UserDto;
import jakarta.persistence.Access;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDto {

    private final ProductService productService;
    private final UserService userService;

    // 생성자 추가

    private Long fanId;
    private UserDto fan;
    private ProductDto goods;
    private Long orderId;

    public OrderDto convertToOrderDto(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();
        Long orderId = orderEntity.getOrderId();
        orderDto.setOrderId(orderId);
        orderDto.setFan(userService.mapToDTO(orderEntity.getFan()));
        orderDto.setGoods(productService.mapToDTO(orderEntity.getGoods()));

        return orderDto;
    }
}
