package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myshop.internetshop.classes.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "orders")
@SuppressWarnings("java:S7027")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @JsonIgnore
    @SuppressWarnings("java:S7027")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Getter
    @OneToMany(
            mappedBy = "order",
            cascade =
                    {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType
                            .REFRESH},
            fetch = FetchType.LAZY
    )
    private List<OrderProduct> products;

    @Column(nullable = false)
    private String orderStatus = OrderStatus.CREATED.getStatus();

    public Order() {
        products = new ArrayList<>();
    }

    public Optional<OrderProduct> getProductById(int productId) {
        for (OrderProduct product : products) {
            if (product.getProduct().getId() == productId) return Optional.of(product);
        }
        return Optional.empty();
    }

}
