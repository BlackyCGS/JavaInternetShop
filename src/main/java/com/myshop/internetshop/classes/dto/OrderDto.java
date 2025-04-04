package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.Product;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDto {

    private int orderId;
    private int orderStatus;
    private int userId;
    private List<Product> products;

    OrderDto() {
        /*Default Constructor*/
    }

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.userId = order.getUser().getId();
        this.products = order.getProducts();
    }
}
