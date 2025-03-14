package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.OrderDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/{id}/addProducts")
    OrderDto addProductsToOrder(@PathVariable int id,
                                        @RequestBody List<Integer> productsId) {
        return orderService.addProductsToOrder(id, productsId);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    @PutMapping("/{id}/changeStatus")
    OrderDto changeStatus(@PathVariable int id, @RequestParam() int status) {
        return orderService.changeStatus(id, status);
    }

    @PostMapping("/create")
    public UserDto createOrder(@RequestParam() int id) {
        return orderService.createOrder(id);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }
}
