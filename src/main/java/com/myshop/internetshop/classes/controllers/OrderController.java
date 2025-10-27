package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.OrderDto;
import com.myshop.internetshop.classes.dto.OrderRequest;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.services.JwtService;
import com.myshop.internetshop.classes.services.OrderService;
import com.myshop.internetshop.classes.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    OrderController(OrderService orderService, JwtService jwtService, UserService userService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Operation(summary = "Get order information using order id")
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Get order by status or/and user id")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getByStatusAndUserId(@RequestParam(required = false) Integer status,
                                               @RequestParam(required = false) Integer userId) {
        return orderService.getByStatusAndUserId(status, userId);
    }

    @Operation(summary = "Create order")
    @PostMapping("/create")
    public UserDto createOrder(@RequestParam() int id) {
        return orderService.createOrder(id);
    }

    @Operation(summary = "Delete order")
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    @Operation(summary = "Change order status")
    @PutMapping("/{id}/changeStatus")
    @PreAuthorize("hasRole('ADMIN')")
    OrderDto changeStatus(@PathVariable int id, @RequestParam() String status) {
        return orderService.changeStatus(id, status);
    }

    @Operation(summary = "Add product to order with entered id")
    @PutMapping("/{id}/addProducts")
    OrderDto addProductsToOrder(@PathVariable("id") int id,
                                @RequestBody List<OrderRequest> products) {
        return orderService.addProductsToOrder(id,
                products.stream().map(OrderRequest::getProductId).toList(),
                products.stream().map(OrderRequest::getQuantity).toList());
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT', 'DELIVERY')")
    ResponseEntity<List<OrderDto>> getAllOrders(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize,
            @RequestParam(required = false, defaultValue = "all") String status
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (status.equals("all")) {
            return ResponseEntity.ok(orderService.getAllOrders(pageable));
        }
        else {
            return ResponseEntity.ok(orderService.getAllOrdersByStatus(status, pageable));
        }
    }

    @Operation(summary = "Add product to cart")
    @PostMapping("cart/{productId}")
    ResponseEntity<OrderDto> addProductToCart(@PathVariable int productId,
            @RequestParam(required = false, defaultValue = "1") int quantity,
            HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        String name = jwtService.extractUsername(token);
        return ResponseEntity.ok(orderService
                .addProductToCart(userService.getUserIdByName(name), productId, quantity));
    }

    @Operation(summary = "Get total number of orders")
    @GetMapping("/amount")
    public ResponseEntity<Integer> getTotalOrders(@RequestParam(required = false,
            defaultValue = "all") String status) {
        if (status.equals("all")) {
            return ResponseEntity.ok(orderService.getOrdersCount());
        }
        return ResponseEntity.ok(orderService.getOrdersCountByStatus(status));
    }

    @GetMapping("/cart")
    public synchronized ResponseEntity<OrderDto> getCart(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        String name = jwtService.extractUsername(token);
        return ResponseEntity.ok(orderService.getCartById(userService.getUserIdByName(name)));
    }

    @DeleteMapping("/cart/{productId}")
    ResponseEntity<OrderDto> deleteProductFromCart(@PathVariable int productId,
                                            HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        String name = jwtService.extractUsername(token);

        return ResponseEntity.ok(orderService.deleteProductFromCart(userService.getUserIdByName(name),
                productId));

    }

    @Operation(summary = "transform cart to order")
    @PutMapping("/cart/toOrder")
    ResponseEntity<OrderDto> cartToOrder(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        String name = jwtService.extractUsername(token);

        return ResponseEntity
                .ok(orderService.convertCartToOrder(userService.getUserIdByName(name)));
    }

    @GetMapping("/myOrders")
    public ResponseEntity<List<OrderDto>> getMyOrders(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        String name = jwtService.extractUsername(token);
        return ResponseEntity.ok(orderService.getOrdersByUserId(userService.getUserIdByName(name)));
    }
}
