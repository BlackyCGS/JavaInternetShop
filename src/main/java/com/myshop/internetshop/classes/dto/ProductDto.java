package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Product;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private int productId;
    private String name;
    private float price;
    private Gpu gpu;

    public ProductDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.gpu = product.getGpu();
    }
    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public Gpu getGpu() {
        return gpu;
    }

    public int getProductId() {
        return productId;
    }

}
