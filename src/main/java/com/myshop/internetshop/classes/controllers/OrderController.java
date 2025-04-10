package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.OrderDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Add product to order with entered id")
    @PutMapping("/{id}/addProducts")
    OrderDto addProductsToOrder(@PathVariable("id") int id,
                                        @RequestBody List<Integer> productsId) {
        return orderService.addProductsToOrder(id, productsId);
    }

    @Operation(summary = "Delete order")
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    @Operation(summary = "Change order status")
    @PutMapping("/{id}/changeStatus")
    OrderDto changeStatus(@PathVariable int id, @RequestParam() int status) {
        return orderService.changeStatus(id, status);
    }

    @Operation(summary = "Create order")
    @PostMapping("/create")
    public UserDto createOrder(@RequestParam() int id) {
        return orderService.createOrder(id);
    }

    @Operation(summary = "Get order information using order id")
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Get order by status or/and user id")
    @GetMapping("/")
    public List<OrderDto> getByStatusAndUserId(@RequestParam(required = false) Integer status,
                                               @RequestParam(required = false) Integer userId) {
        return orderService.getByStatusAndUserId(status, userId);
    }
}
