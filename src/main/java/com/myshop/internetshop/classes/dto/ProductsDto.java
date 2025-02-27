package com.myshop.internetshop.classes.dto;

public record ProductsDto(int productId, String name, int price) {
    public String getName() {
        return name;
    }
}
