package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Order;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderDto {

    private int orderId;
    private String orderStatus;
    private int userId;
    private List<OrderProductDto> products;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.userId = order.getUser().getId();
        this.products = order.getProducts().stream().map(OrderProductDto::new).toList();
    }
}
