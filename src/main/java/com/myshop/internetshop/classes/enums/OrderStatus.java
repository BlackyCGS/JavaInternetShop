package com.myshop.internetshop.classes.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED("CREATED"),
    CONFIRMED("CONFIRMED"),
    PROCESSED("PROCESSED"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED"),
    CART("CART");
    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
