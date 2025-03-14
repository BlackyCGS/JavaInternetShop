package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.OrderDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductsService productsService;
    private final UserService userService;
    private static final String NOT_FOUND_MESSAGE = "Order not found";
    OrderService(OrderRepository orderRepository, ProductsService productsService,
                 UserService userService) {
        this.orderRepository = orderRepository;
        this.productsService = productsService;
        this.userService = userService;
    }

    public OrderDto addProductsToOrder(int orderId, List<Integer> productsId) {
        if(orderRepository.existsById(orderId)) {
            Order order = orderRepository.findByOrderId(orderId);
            List<Product> products = order.getProducts();
            for (Integer productId : productsId) {
                products.add(productsService.getProductById(productId));
            }
            order.setProducts(products);
            order = orderRepository.save(order);
            return new OrderDto(order);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
    }

    public OrderDto changeStatus(int id, int status) {
        if(orderRepository.existsById(id)) {
            Order order = orderRepository.findByOrderId(id);
            order.setOrderStatus(status);
            orderRepository.save(order);
            return new OrderDto(order);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        }
    }

    public UserDto createOrder(int userId) {
        if(userService.existsById(userId)) {
            Order order = new Order();
            order.setOrderStatus(0);
            order.setUser(userService.findByUserId(userId));
            orderRepository.save(order);
            return new UserDto(userService.findByUserId(userId));
        } else {
            throw new NotFoundException("User does not exist");
        }
    }

    public OrderDto getOrderById(int orderId) {
        if(orderRepository.existsById(orderId)) {
            Order order = orderRepository.findByOrderId(orderId);
            return new OrderDto(order);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }
}
