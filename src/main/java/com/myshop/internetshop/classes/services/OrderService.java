package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.cache.Cache;
import com.myshop.internetshop.classes.dto.OrderDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.Order;
import com.myshop.internetshop.classes.entities.OrderProduct;
import com.myshop.internetshop.classes.entities.OrdersProductsId;
import com.myshop.internetshop.classes.enums.OrderStatus;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public OrderDto addProductsToOrder(int orderId, List<Integer> productsId,
                                       List<Integer> quantities) {
        if (orderRepository.existsById(orderId)) {
            Order order = orderRepository.findById(orderId);
            List<OrderProduct> products = order.getProducts();
            for (int i = 0; i < productsId.size(); i++) {
                Integer productId = productsId.get(i);
                    Optional<OrderProduct> product = order.getProductById(productId);
                    if(product.isPresent()) {
                        product.get().setQuantity(quantities.get(i));
                        Optional<OrderProduct> finalProduct = product;
                        products.replaceAll(listProduct ->
                                listProduct.getProduct().getId() == productId
                                        ? finalProduct.get()
                                        : listProduct
                        );
                    } else {
                        product = Optional.of(new OrderProduct());
                        OrdersProductsId id = new OrdersProductsId(orderId, productId);
                        product.get().setOrdersProductsId(id);
                        product.get().setOrder(order);
                        product.get().setProduct(productsService.getProductById(productId));
                        product.get().setQuantity(quantities.get(i));
                        products.add(product.get());
                    }
            }
            order.setProducts(products);
            order = orderRepository.save(order);
            String cacheKey = ORDER_CACHE_KEY + orderId;
            orderCache.put(cacheKey, order);
            return new OrderDto(order);
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE + orderId);
    }

    public Order deleteProductFromOrder(int orderId, Integer productId) {
        if (orderRepository.existsById(orderId)) {
            Order order = orderRepository.findById(orderId);
            List<OrderProduct> products = order.getProducts();
            products.removeIf(p -> p.getProduct().getId() == productId);
            order.setProducts(products);
            return orderRepository.save(order);
        }
        throw new NotFoundException("There is no order");
    }

    public void deleteOrder(int orderId) {
        String cacheKey = ORDER_CACHE_KEY + orderId;
        if (orderCache.contains(cacheKey)) {
            orderCache.remove(cacheKey);
        }
        logger.info("Order with id {} deleted", orderId);
        orderRepository.deleteById(orderId);
    }

    public OrderDto changeStatus(int id, String status) {
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
            order.setOrderStatus(OrderStatus.CREATED.getStatus());
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
        List<Order> orders = orderRepository.findByOrderStatusAndUserId(userId, status);
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

    public List<OrderDto> getAllOrders(Pageable pageable) {
        return orderRepository.findByOrderStatusNot(OrderStatus.CART.getStatus(),
                pageable)
                .stream()
                .map(OrderDto::new)
                .toList();
    }

    public List<OrderDto> getAllOrdersByStatus(String status, Pageable pageable) {
        return  orderRepository.findByOrderStatus(status, pageable)
                .stream()
                .map(OrderDto::new)
                .toList();
    }

    public Order createCart(int userId) {
        if (userService.existsById(userId)) {
            Order order = new Order();
            order.setOrderStatus(OrderStatus.CART.getStatus());
            order.setUser(userService.findByUserId(userId));
            order = orderRepository.save(order);
            return order;
        }
        throw new NotFoundException("There is no such user. Id: " + userId);
    }

    public OrderDto addProductToCart(Integer userId, int productId, int quantity) {
        Optional<Order> cart = orderRepository
                .findByUserIdAndOrderStatus(userId, OrderStatus.CART.getStatus());
        if (cart.isEmpty()) {
            cart = Optional.ofNullable(createCart(userId));
        }
        if (cart.isPresent()) {
            addProductsToOrder(cart.get().getId(), List.of(productId), List.of(quantity));
            return new OrderDto(cart.get());
        }
        throw new NotFoundException("There is to user or product with respective ids: "
                + userId + ", " + productId);
    }

    public OrderDto deleteProductFromCart(Integer userId, Integer productId) {
        Optional<Order> cart = orderRepository
                .findByUserIdAndOrderStatus(userId, OrderStatus.CART.getStatus());

        if (cart.isEmpty()) {
            throw new NotFoundException("There is no such cart.");
        }
        return new OrderDto(deleteProductFromOrder(cart.get().getId(), productId));
    }

    public OrderDto getCartById(int userId) {
        Optional<Order> cart = orderRepository
                .findByUserIdAndOrderStatus(userId, OrderStatus.CART.getStatus());
        if (cart.isEmpty()) {
            cart = Optional.ofNullable(createCart(userId));
        }
        if (cart.isPresent()) {
            return new OrderDto(cart.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE + userId);
    }

    public int getOrdersCount() {
        return orderRepository.countByOrderStatusNot(OrderStatus.CART.getStatus());
    }

    public int getOrdersCountByStatus(String status) {
        return orderRepository.countByOrderStatus(status);
    }

    @Transactional
    public OrderDto convertCartToOrder(int userId) {
        Optional<Order> cart = orderRepository
                .findByUserIdAndOrderStatus(userId, OrderStatus.CART.getStatus());
        if (cart.isEmpty()) {
            throw new NotFoundException("There is no such cart.");
        }
        Order order = cart.get();
        List<OrderProduct> products = order.getProducts();
        for (OrderProduct product : products) {
            productsService.decreaseStock(product.getProduct().getId(), product.getQuantity());
        }
        order.setOrderStatus(OrderStatus.CREATED.getStatus());
        return new OrderDto(orderRepository.save(order));
    }

    public List<OrderDto> getOrdersByUserId(int userId) {
        return orderRepository.findByUserIdAndOrderStatusNot(userId,
                OrderStatus.CART.getStatus()).stream().map(OrderDto::new).toList();
    }
}
