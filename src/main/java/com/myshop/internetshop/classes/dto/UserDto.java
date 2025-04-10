package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.User;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private final int id;

    @NotBlank(message = "User name can not be blank")
    private final String name;

    @NotBlank(message = "Email cannot be blank")
    @Email
    private final String email;

    @NotBlank
    private final List<OrderDto> orders = new ArrayList<>();

    @NotBlank
    private final String password;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        List<Order> userOrders = user.getOrders();

        for (Order userOrder : userOrders) {
            this.orders.add(new OrderDto(userOrder));
        }
    }

}
