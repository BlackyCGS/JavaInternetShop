package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private final int id;
    private final String name;
    private final String email;
    private final List<OrderDto> orders = new ArrayList<>();

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        List<Order> userOrders = user.getOrders();

        for (Order userOrder : userOrders) {
            this.orders.add(new OrderDto(userOrder));
        }
    }

}
