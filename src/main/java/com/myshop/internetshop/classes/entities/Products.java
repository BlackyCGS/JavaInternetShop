package com.myshop.internetshop.classes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @Column(nullable = false)
    private int productId;

    private String name;

    private int price;

    public Products(int tableId, int productId, String name, int price) {
        this.productId = productId + tableId * 1000;
        this.name = name;
        this.price = price;
    }

    public Products() {}

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
