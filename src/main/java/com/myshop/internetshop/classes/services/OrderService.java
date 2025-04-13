package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.cache.Cache;
import com.myshop.internetshop.classes.dto.OrderDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ProductsService productsService;
    private final UserService userService;
    private static final String NOT_FOUND_MESSAGE = "There is no order with id ";
    private final Cache<Order> orderCache;
    private static final String ORDER_CACHE_KEY = "order-";

    OrderService(OrderRepository orderRepository, ProductsService productsService,
                 UserService userService, Cache<Order> orderCache) {
        this.orderRepository = orderRepository;
        this.productsService = productsService;
        this.userService = userService;
        this.orderCache = orderCache;
    }

    public OrderDto addProductsToOrder(int orderId, List<Integer> productsId) {
        if (orderRepository.existsById(orderId)) {
            Order order = orderRepository.findById(orderId);
            List<Product> products = order.getProducts();
            for (Integer productId : productsId) {
                if (productsService.existsById(productId)) {
                    products.add(productsService.getProductById(productId));
                }
            }
            order.setProducts(products);
            order = orderRepository.save(order);
            String cacheKey = ORDER_CACHE_KEY + orderId;
            if (orderCache.contains(cacheKey)) {
                orderCache.remove(cacheKey);
            }
            orderCache.put(cacheKey, order);
            return new OrderDto(order);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE + orderId);
        }
    }

    public void deleteOrder(int orderId) {
        String cacheKey = ORDER_CACHE_KEY + orderId;
        if (orderCache.contains(cacheKey)) {
            orderCache.remove(cacheKey);
        }
        logger.info("Order with id {} deleted", orderId);
        orderRepository.deleteById(orderId);
    }

    public OrderDto changeStatus(int id, int status) {
        if (orderRepository.existsById(id)) {
            Order order = orderRepository.findById(id);
            order.setOrderStatus(status);
            order = orderRepository.save(order);
            String cacheKey = ORDER_CACHE_KEY + order.getId();
            if (orderCache.contains(cacheKey)) {
                orderCache.remove(cacheKey);
            }
            orderCache.put(cacheKey, order);
            return new OrderDto(order);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE + id);
        }
    }

    public UserDto createOrder(int userId) {
        if (userService.existsById(userId)) {
            Order order = new Order();
            order.setOrderStatus(0);
            order.setUser(userService.findByUserId(userId));
            order = orderRepository.save(order);
            String cacheKey = ORDER_CACHE_KEY + order.getId();
            if (orderCache.contains(cacheKey)) {
                orderCache.remove(cacheKey);
            }
            orderCache.put(cacheKey, order);
            return new UserDto(userService.findByUserId(userId));
        } else {
            throw new NotFoundException("User does not exist");
        }
    }

    public OrderDto getOrderById(int orderId) {
        String cacheKey = ORDER_CACHE_KEY + orderId;
        if (orderCache.contains(cacheKey)) {
            return new OrderDto(orderCache.get(cacheKey));
        }
        if (orderRepository.existsById(orderId)) {
            Order order = orderRepository.findById(orderId);
            orderCache.put(cacheKey, order);
            return new OrderDto(order);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE + orderId);
        }
    }

    public List<OrderDto> getByStatusAndUserId(Integer status, Integer userId) {
        List<Order> orders = orderRepository.findByOrderStatus(userId, status);
        List<OrderDto> orderDtos = new ArrayList<>();
        if (orders.isEmpty()) {
            throw new NotFoundException("There is no such orders");
        }
        for (Order order : orders) {
            orderDtos.add(new OrderDto(order));
            orderCache.put(ORDER_CACHE_KEY + order.getId(), order);
        }
        return orderDtos;
    }
}
