package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.entities.User;

import java.util.List;

public class OrderRequest {
    private int orderId;
    private int orderStatus;
    private int userId;
    private List<Product> products;
    private User user;
    OrderRequest() {
        /*Default Constructor*/
    }

    public OrderRequest(Order order) {
        this.orderId = order.getOrderId();
        this.orderStatus = order.getOrderStatus();
        this.userId = order.getUser().getUserId();
        this.products = order.getProducts();
        this.user = order.getUser();
    }

    OrderRequest(int orderId, int orderStatus, int userId, List<Product> products, User user) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.products = products;
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }
}
