package com.myshop.internetshop.classes.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int categoryId;

    private String name;

    private float price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Gpu gpu;

    @ManyToMany(fetch = FetchType.LAZY, cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType
            .REFRESH},
            targetEntity = Order.class)
    @JoinTable(name = "orders_products",
            inverseJoinColumns = @JoinColumn(name = "order_id"),
            joinColumns = @JoinColumn(name = "product_id"),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private List<Order> orders;

    public Product(int categoryId, int id, String name, int price) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
    }

    public Product() {}

    public int getId() {
        return id;
    }

    public void setId(int productId) {
        this.id = productId;
    }

    public int getCategoryId() {return categoryId; }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setGpu(Gpu gpu) {
        this.gpu = gpu;
    }

    public Gpu getGpu() {
        return gpu;
    }
}
