package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.OrderProduct;
import lombok.Data;

@Data
public class OrderProductDto {

    private ProductDto product;
    private int quantity;

    public OrderProductDto(OrderProduct product) {
        this.product = new ProductDto(product.getProduct());
        this.quantity = product.getQuantity();
    }
}
