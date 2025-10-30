package com.myshop.internetshop.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SsdFilter {
    Float minPrice;
    Float maxPrice;
    Integer minSize;
    Integer maxSize;
    String protocol;
    String formFactor;
}
