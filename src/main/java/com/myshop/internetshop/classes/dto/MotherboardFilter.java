package com.myshop.internetshop.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MotherboardFilter {
    Float minPrice;
    Float maxPrice;
    String socket;
    String chipset;
    String formFactor;
    String memoryType;
}
