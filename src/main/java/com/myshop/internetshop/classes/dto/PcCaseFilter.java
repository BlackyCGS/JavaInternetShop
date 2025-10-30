package com.myshop.internetshop.classes.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PcCaseFilter {

    Float minPrice;
    Float maxPrice;
    String motherboard;
    String powerSupply;
}
