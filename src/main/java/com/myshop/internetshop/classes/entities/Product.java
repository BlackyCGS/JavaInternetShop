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

    private String name;

    private float price;

    @Getter
    @Setter
    @SuppressWarnings("javaarchitecture:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Gpu gpu;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @SuppressWarnings("javaarchitecture:S7027")
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

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product() {}
}
