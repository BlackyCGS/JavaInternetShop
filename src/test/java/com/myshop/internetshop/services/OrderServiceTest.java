package com.myshop.internetshop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.cache.Cache;
import com.myshop.internetshop.classes.dto.OrderDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.enums.OrderStatus;
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

        @Mock
        private Cache<Order> orderCache;

        @InjectMocks
        private OrderService orderService;

        private Order testOrder;
        private User testUser;
        private Product testProduct;

        @BeforeEach
        void setUp() {
            testUser = new User();
            testUser.setId(1);

            testProduct = new Product();
            testProduct.setId(1);

            testOrder = new Order();
            testOrder.setId(1);
            testOrder.setUser(testUser);
            testOrder.setOrderStatus(OrderStatus.PROCESSED.getStatus());
            testOrder.setProducts(new ArrayList<>());
        }

    @Test
    void addProductsToOrder_OrderExists_AddsProducts() {
        // Arrange
        List<Integer> productIds = List.of(1, 2);
        List<Integer> quantities = List.of(1, 1);
        when(orderRepository.existsById(1)).thenReturn(true);
        when(orderRepository.findById(1)).thenReturn(testOrder);


        Product product1 = new Product();
        product1.setId(1);
        Product product2 = new Product();
        product2.setId(2);

        when(productsService.existsById(1)).thenReturn(true);
        when(productsService.existsById(2)).thenReturn(true);
        when(productsService.getProductById(1)).thenReturn(product1);
        when(productsService.getProductById(2)).thenReturn(product2);

        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
        when(orderCache.contains("order-1")).thenReturn(true);

        // Act
        OrderDto result = orderService.addProductsToOrder(1, productIds, quantities);

        // Assert
        assertNotNull(result);
        verify(orderRepository).save(testOrder);
        verify(orderCache).contains("order-1");
        verify(orderCache).remove("order-1");
        verify(orderCache).put("order-1", testOrder);

        // Дополнительная проверка, что продукты были добавлены
        assertEquals(2, testOrder.getProducts().size());
    }

    @Test
    void addProductsToOrder_OrderNotExists_ThrowsException() {
        // Arrange
        List<Integer> testList = List.of(1);
        List<Integer> quantities = List.of(1, 1);
        when(orderRepository.existsById(1)).thenReturn(false);

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                orderService.addProductsToOrder(1, testList, quantities));

        // Assert
        assertEquals("There is no order with id 1", exception.getMessage());
    }

        @Test
        void deleteOrder_DeletesFromCacheAndRepository() {
            // Arrange
            when(orderCache.contains("order-1")).thenReturn(true);

            // Act
            orderService.deleteOrder(1);

            // Assert
            verify(orderCache).remove("order-1");
            verify(orderRepository).deleteById(1);
        }

        @Test
        void changeStatus_OrderExists_UpdatesStatus() {
            // Arrange
            when(orderRepository.existsById(1)).thenReturn(true);
            when(orderRepository.findById(1)).thenReturn(testOrder);
            when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
            when(orderCache.contains("order-1")).thenReturn(true); // Добавляем мок для contains

            // Act
            OrderDto result = orderService.changeStatus(1, OrderStatus.CONFIRMED.getStatus());

            // Assert
            assertNotNull(result);
            assertEquals(OrderStatus.CONFIRMED.getStatus(), result.getOrderStatus());
            verify(orderCache).contains("order-1");
            verify(orderCache).remove("order-1");
            verify(orderCache).put("order-1", testOrder);
        }

        @Test
        void changeStatus_OrderNotExists_ThrowsException() {
            // Arrange
            when(orderRepository.existsById(1)).thenReturn(false);

            // Act & Assert
            assertThrows(NotFoundException.class, () ->
                    orderService.changeStatus(1, "CONFIRMED"));
        }

        @Test
        void createOrder_UserExists_CreatesOrder() {
            // Arrange
            when(userService.existsById(1)).thenReturn(true);
            when(userService.findByUserId(1)).thenReturn(testUser);
            when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

            // Act
            UserDto result = orderService.createOrder(1);

            // Assert
            assertNotNull(result);
            verify(orderRepository).save(any(Order.class));
            verify(orderCache).put("order-1", testOrder);
        }

        @Test
        void createOrder_UserNotExists_ThrowsException() {
            // Arrange
            when(userService.existsById(1)).thenReturn(false);

            // Act & Assert
            assertThrows(NotFoundException.class, () ->
                    orderService.createOrder(1));
        }

        @Test
        void getOrderById_OrderInCache_ReturnsCachedOrder() {
            // Arrange
            when(orderCache.contains("order-1")).thenReturn(true);
            when(orderCache.get("order-1")).thenReturn(testOrder);

            // Act
            OrderDto result = orderService.getOrderById(1);

            // Assert
            assertNotNull(result);
            verify(orderRepository, never()).findById(1);
        }

        @Test
        void getOrderById_OrderNotInCacheButInDb_ReturnsOrder() {
            // Arrange
            when(orderCache.contains("order-1")).thenReturn(false);
            when(orderRepository.existsById(1)).thenReturn(true);
            when(orderRepository.findById(1)).thenReturn(testOrder);

            // Act
            OrderDto result = orderService.getOrderById(1);

            // Assert
            assertNotNull(result);
            verify(orderCache).put("order-1", testOrder);
        }

        @Test
        void getOrderById_OrderNotExists_ThrowsException() {
            // Arrange
            when(orderCache.contains("order-1")).thenReturn(false);
            when(orderRepository.existsById(1)).thenReturn(false);

            // Act & Assert
            assertThrows(NotFoundException.class, () ->
                    orderService.getOrderById(1));
        }

        @Test
        void getByStatusAndUserId_OrdersExist_ReturnsOrderDtos() {
            // Arrange
            List<Order> orders = List.of(testOrder);
            when(orderRepository.findByOrderStatus(1, 0)).thenReturn(orders);

            // Act
            List<OrderDto> result = orderService.getByStatusAndUserId(0, 1);

            // Assert
            assertFalse(result.isEmpty());
            verify(orderCache).put("order-1", testOrder);
        }

        @Test
        void getByStatusAndUserId_NoOrders_ThrowsException() {
            // Arrange
            when(orderRepository.findByOrderStatus(1, 0)).thenReturn(new ArrayList<>());

            // Act & Assert
            assertThrows(NotFoundException.class, () ->
                    orderService.getByStatusAndUserId(0, 1));
        }
}
