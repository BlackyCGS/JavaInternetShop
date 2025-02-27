package com.myshop.internetshop.classes.dto;


public record ProductDataDto(int price, String name) {
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

/*
CPU: Freq Cores Flows
GPU: CUDA cores Freq
RAM: Freq Size 2/4 channels
Keyboard: Form type
 */