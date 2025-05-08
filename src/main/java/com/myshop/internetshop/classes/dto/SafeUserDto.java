package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SafeUserDto {

    private int id;

    @NotBlank(message = "User name can not be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;

    private List<OrderDto> orders = new ArrayList<>();

    private String role;

    public SafeUserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        List<Order> userOrders = user.getOrders();
        this.role = user.getPermission();
        for (Order userOrder : userOrders) {
            this.orders.add(new OrderDto(userOrder));
        }
    }

}
