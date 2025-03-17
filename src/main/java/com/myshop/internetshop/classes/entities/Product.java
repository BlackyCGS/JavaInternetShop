package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int categoryId;

    private String name;

    private float price;

    @Getter
    @Setter
    @OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Gpu gpu;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.LAZY, cascade =
        {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType
            .REFRESH},
            targetEntity = Order.class)
    @JoinTable(name = "orders_products",
            inverseJoinColumns = @JoinColumn(name = "order_id"),
            joinColumns = @JoinColumn(name = "product_id"),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @JsonIgnore
    private List<Order> orders;

    public Product(int categoryId, int id, String name, int price) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
    }

    public Product() {}
}
