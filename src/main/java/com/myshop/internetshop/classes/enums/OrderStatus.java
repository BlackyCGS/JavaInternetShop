package com.myshop.internetshop.classes.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED(0),
    CONFIRMED(1),
    PROCESSED(2),
    DELIVERED(3),
    CANCELED(4);
    private final int status;

    OrderStatus(int status) {
        this.status = status;
    }
}
