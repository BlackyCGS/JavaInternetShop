package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.User;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private final int id;
    private final String name;
    private final String email;
    private final List<OrderDto> orders = new ArrayList<>();

    public UserDto(int id, String name, String email, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.email = email;
        for(Order order : orders) {
            this.orders.add(new OrderDto(order));
        }
    }

    public UserDto(User user) {
        this.id = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        List<Order> userOrders = user.getOrders();
        for(Order userOrder : userOrders) {
            this.orders.add(new OrderDto(userOrder));
        }
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

}
