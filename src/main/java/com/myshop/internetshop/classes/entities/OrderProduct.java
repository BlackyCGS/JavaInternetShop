package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "orders_products")
@Getter
@Setter
public class OrderProduct {

    @EmbeddedId
    @JsonIgnore
    OrdersProductsId ordersProductsId;

    @ManyToOne(fetch = FetchType.LAZY, cascade =
    {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType
            .REFRESH})
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType
                    .REFRESH})
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @Column
    private int quantity;

    public OrderProduct() {}

    public OrderProduct(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(order, that.order) &&
                quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order, quantity);
    }
}
