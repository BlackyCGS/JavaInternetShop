package com.myshop.internetshop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.dto.OrderDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.OrderRepository;
import com.myshop.internetshop.classes.services.OrderService;
import com.myshop.internetshop.classes.services.ProductsService;
import com.myshop.internetshop.classes.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductsService productsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private User user;
    private final int orderId = 1;
    private final int userId = 1;
    private final int productId = 200;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(orderId);
        order.setOrderStatus(0);
        order.setProducts(new ArrayList<>());
        user = new User();
        user.setId(1);
        user.setName("JohnDoe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        order.setUser(user);
    }

    @Test
    void testAddProductsToOrder_Success() {
        List<Integer> productIds = List.of(productId);
        Product product = new Product();
        product.setId(productId);

        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.findById(orderId)).thenReturn(order);
        when(productsService.existsById(productId)).thenReturn(true);
        when(productsService.getProductById(productId)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto result = orderService.addProductsToOrder(orderId, productIds);

        assertNotNull(result);
        assertEquals(1, order.getProducts().size());
        verify(orderRepository).save(order);
    }

    @Test
    void testAddProductsToOrder_OrderNotFound() {
        List<Integer> ids = List.of(productId);
        when(orderRepository.existsById(orderId)).thenReturn(false);
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderService.addProductsToOrder(orderId, ids));

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).existsById(orderId);
    }

    @Test
    void testDeleteOrder_Success() {
        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteOrder(orderId);

        verify(orderRepository).deleteById(orderId);
    }

    @Test
    void testChangeStatus_Success() {
        int newStatus = 1;

        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.findById(orderId)).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto result = orderService.changeStatus(orderId, newStatus);

        assertNotNull(result);
        assertEquals(newStatus, order.getOrderStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void testChangeStatus_OrderNotFound() {
        when(orderRepository.existsById(orderId)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> orderService.changeStatus(orderId, 1));

        assertEquals("404 NOT_FOUND \"Order not found\"", exception.getMessage());
    }

    @Test
    void testCreateOrder_Success() {
        User mockUser = new User();
        mockUser.setId(userId);

        when(userService.existsById(userId)).thenReturn(true);
        when(userService.findByUserId(userId)).thenReturn(mockUser);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        UserDto result = orderService.createOrder(userId);

        assertNotNull(result);
        verify(orderRepository).save(any(Order.class));
    }


    @Test
    void testCreateOrder_UserNotFound() {
        when(userService.existsById(userId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderService.createOrder(userId));

        assertEquals("User does not exist", exception.getMessage());
    }

    @Test
    void testGetOrderById_Success() {
        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.findById(orderId)).thenReturn(order);

        OrderDto result = orderService.getOrderById(orderId);

        assertNotNull(result);
        verify(orderRepository).findById(orderId);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.existsById(orderId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderService.getOrderById(orderId));

        assertEquals("Order not found", exception.getMessage());
    }
}
