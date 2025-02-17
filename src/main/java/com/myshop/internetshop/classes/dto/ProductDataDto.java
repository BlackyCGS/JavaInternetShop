package com.myshop.internetshop.classes.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductDataDto {

    public int price;
    public String name;
    public ProductDataDto(int price, String name) {
        this.price = price;
        this.name = name;
    }
    public int getPrice() {return price;}
    public String getName() {return name;}
}
