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
        this.orderId = order.getOrderId();
        this.orderStatus = order.getOrderStatus();
        this.userId = order.getUser().getUserId();
        this.products = order.getProducts();
    }

    OrderDto(int orderId, int orderStatus, int userId, List<Product> products) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.products = products;
    }

    public Order toOrder() {
        Order order = new Order();
        order.setOrderStatus(orderStatus);
        order.setProducts(products);
        return order;
    }
}
